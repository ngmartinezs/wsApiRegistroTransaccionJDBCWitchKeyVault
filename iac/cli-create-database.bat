cls
@ECHO OFF
ECHO "*********************************************************"
ECHO "* @date: 31/08/2022                                     *"
ECHO "* @autor:ngmartinezs@gmail.com                          *"
ECHO "* @target: Script para la creacion de una base de       *"
ECHO "*          datos que permita soportar las transacciones *"
ECHO "*          de un micro servicios CRUD                   *"
ECHO "*********************************************************"


ECHO "---------------------------------------------------------"
ECHO "- DEFINICION DE VARIABLES DE ENTORNO                    -"
ECHO "---------------------------------------------------------"
SET AZ_SUSCRIPTION=%1
SET AZ_RESOURCE_GROUP=%2
SET AZ_LOCATION=%3
SET AZ_SERVER_DATABASE_NAME=srv-%4
SET AZ_DATABASE_NAME=%4
SET AZ_SQL_SERVER_USERNAME=%5
SET AZ_SQL_SERVER_PASSWORD=%6
SET AZ_TAG=%7
SET AZ_LOCAL_IP_ADDRESS=%8
SET PARAM_CREATE_RESOURCE=%9

SET APPID =""
SET URIKEYVAULT=""


ECHO "AZ_RESOURCE_GROUP=%AZ_RESOURCE_GROUP%  & AZ_DATABASE_NAME=%AZ_DATABASE_NAME% & AZ_LOCATION=%AZ_LOCATION% & AZ_SQL_SERVER_USERNAME=%AZ_SQL_SERVER_USERNAME% & AZ_SQL_SERVER_PASSWORD=%AZ_SQL_SERVER_PASSWORD% & AZ_LOCAL_IP_ADDRESS=%AZ_LOCAL_IP_ADDRESS% & PARAM_CREATE_RESOURCE= %PARAM_CREATE_RESOURCE%"

ECHO "---------------------------------------------------------"
ECHO "- SE CONECTA A LA SUSCRIPCION                           -"
ECHO "---------------------------------------------------------"
CALL AZ account set -s %AZ_SUSCRIPTION%

ECHO "---------------------------------------------------------"
ECHO "- SE CREA CUENTA PRINCIPAL                              -"
ECHO "---------------------------------------------------------"
call AZ AD SP CREATE-FOR-RBAC --name acc-ser-%AZ_DATABASE_NAME% --role Contributor --scopes /subscriptions/%AZ_SUSCRIPTION% --query appId password -o tsv > dat.tmp
set /p APPID=<dat.tmp
del dat.tmp
ECHO "IMPORTANTE appId Generated => " %APPID% 

IF %PARAM_CREATE_RESOURCE% == DELETE (
    GOTO DELETE_RESOURCE_GROUP
) ELSE IF %PARAM_CREATE_RESOURCE% == DELETE_CREATE (
    GOTO DELETE_RESOURCE_GROUP
) ELSE IF %PARAM_CREATE_RESOURCE% == CREATE_KEY_VAULT (
    GOTO CREATE_KEY_VAULT
) ELSE GOTO VALIDATE_CREATE

:DELETE_RESOURCE_GROUP
ECHO "---------------------------------------------------------"
ECHO "- SE ELIMINA EL GRUPO DE RECURSOS                          -"
ECHO "---------------------------------------------------------"
Call AZ GROUP DELETE --name %AZ_RESOURCE_GROUP% -y --output tsv

:VALIDATE_CREATE
IF %PARAM_CREATE_RESOURCE% == CREATE (
    GOTO CREATE_RESOURCE_GROUP
) ELSE IF %PARAM_CREATE_RESOURCE% == DELETE_CREATE (
    GOTO CREATE_RESOURCE_GROUP
) ELSE GOTO END

:CREATE_RESOURCE_GROUP
ECHO "---------------------------------------------------------"
ECHO "- SE CREA EL GRUPO DE RECURSOS                          -"
ECHO "---------------------------------------------------------"
Call AZ GROUP CREATE --name %AZ_RESOURCE_GROUP% --location %AZ_LOCATION% --output tsv

ECHO "---------------------------------------------------------"
ECHO "- SE CREA SERVER DE BASE  DE DATOS                       -"
ECHO "---------------------------------------------------------"
Call AZ SQL SERVER CREATE --resource-group %AZ_RESOURCE_GROUP% --name %AZ_SERVER_DATABASE_NAME% --location %AZ_LOCATION% --admin-user %AZ_SQL_SERVER_USERNAME% --admin-password %AZ_SQL_SERVER_PASSWORD% --output tsv


ECHO "---------------------------------------------------------"
ECHO "- SE CONFIGURAN REGLAS DE FRW SERVER DE BASE  DE DATOS   -"
ECHO "---------------------------------------------------------"
CALL AZ SQL SERVER FIREWALL-RULE CREATE --resource-group %AZ_RESOURCE_GROUP% --name %AZ_SERVER_DATABASE_NAME%-database-ip-allow --server %AZ_SERVER_DATABASE_NAME% --start-ip-address %AZ_LOCAL_IP_ADDRESS% --end-ip-address %AZ_LOCAL_IP_ADDRESS% --output tsv

ECHO "---------------------------------------------------------"
ECHO "- SE CREA BASE DE DATOS                                 -"
ECHO "---------------------------------------------------------"
CALL AZ SQL DB CREATE --resource-group %AZ_RESOURCE_GROUP% --name %AZ_DATABASE_NAME%-database --server %AZ_SERVER_DATABASE_NAME%  --output tsv

:CREATE_KEY_VAULT
ECHO "---------------------------------------------------------"
ECHO "- SE CREA KEYVAULT                                      -"
ECHO "---------------------------------------------------------"
CALL AZ KEYVAULT DELETE --name %AZ_DATABASE_NAME% --resource-group %AZ_RESOURCE_GROUP% --no-wait
CALL AZ KEYVAULT PURGE --name %AZ_DATABASE_NAME% --subscription %AZ_SUSCRIPTION%
CALL AZ KEYVAULT CREATE --resource-group %AZ_RESOURCE_GROUP% --name %AZ_DATABASE_NAME% --enabled-for-deployment true --enabled-for-disk-encryption true  --enabled-for-template-deployment true --location %AZ_LOCATION% --enable-soft-delete false  --query properties.vaultUri --sku standard > dat.tmp
SET /p URIKEYVAULT=<dat.tmp
del dat.tmp

CALL AZ KEYVAULT set-policy --name %AZ_DATABASE_NAME%  --spn %APPID% --secret-permissions get list

ECHO "---------------------------------------------------------"
ECHO "- SE ADICIONAN SECRETS                                  -"
ECHO "---------------------------------------------------------"
CALL AZ KEYVAULT SECRET SET --name "connectionString" --vault-name=%AZ_DATABASE_NAME%  --value="jdbc:sqlserver://%AZ_SERVER_DATABASE_NAME%.database.windows.net:1433;database=%AZ_DATABASE_NAME%-database;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
CALL AZ KEYVAULT SECRET SET --name "usernameDB" --vault-name=%AZ_DATABASE_NAME%  --value=%AZ_SQL_SERVER_USERNAME%
CALL AZ KEYVAULT SECRET SET --name "passwordDB" --vault-name=%AZ_DATABASE_NAME%  --value=%AZ_SQL_SERVER_PASSWORD%

:END