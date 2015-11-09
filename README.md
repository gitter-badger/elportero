# El Portero

[![Build Status](https://travis-ci.org/el-bombillo/elportero.svg?branch=master)](https://travis-ci.org/el-bombillo/elportero)
[![codecov.io](https://codecov.io/github/el-bombillo/elportero/coverage.svg?branch=master)](https://codecov.io/github/el-bombillo/elportero?branch=master)

RESTful authentication service. 

Probably will be paired with [Kong](https://github.com/Mashape/kong) as an API gateway. This service will ...

1. Authenticate users in an existing environment (e.g. LDAP) with an easy plugin architecture
2. Create a consumer in Kong for the authenticated user and return a created token

## Run

1. Download & Install [Java](http://www.oracle.com/technetwork/java/javase/downloads/) >= 1.8
2. Download & Install [Gradle](http://gradle.org/)
3. Start service by running 
   ```
   sh $ gradle run
   ```
4. Use API URL `http://localhost:2760/`