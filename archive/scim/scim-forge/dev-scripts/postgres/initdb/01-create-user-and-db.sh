#!/bin/sh

createdb scimforge_db
createuser scimforge_admin
psql -d scimforge_db --command "alter user scimforge_admin with encrypted password 'password'"
psql -d scimforge_db --command "grant all privileges on database scimforge_db to scimforge_admin"