## Table of Contents

- [Goal](#goal)
- [Postcard-API explorer](#postcard-api-explorer)
- [Build and start postcardsender](#build-and-start-postcardsender)
- [Configure settings](#configure-settings)
- [Start client on separate port for testing an debugging Angular](#start-client-on-separate-port-for-testing-an-debugging-angular)
- [Create our own webclient](#create-our-own-webclient)
- [Used tools and frameworks](#used-tools-and-frameworks)

------------------------------------
## Goal
This project is realized as a part of my masters thesis.

The Swiss Post offers their business customers an API to create postcards.

**With this project I would like to simplify the integration of the API in your own project by providing an example which can be used and adopted.**

Further information about the postcard API, provided by Swiss Post, you will find [here](https://www.post.ch/de/geschaeftlich/prozesse-optimieren/zwischen-ihnen-und-der-post/postkarten-api) 
or the technical specification at: [Postcard API](https://developer.post.ch/en/technical-specifications-of-postcard-api)

## Postcard-API explorer

To get familiar with the possible requests and the following responses of the Postcard API I provided a html-file:
[PostcardAPICommunicator.html](https://github.com/Jonas-Schild/postcardsender/blob/master/PostcardAPICommunicator.html)

You just have to open this file in your browser.
To be able to communicate with the Postcard API, you have to disable Cross Origin Request Security (CORS) in your browser, to allow external content.

```
Start Chrome with disabled web-security:
chrome.exe --user-data-dir="C:/Chrome dev session" --disable-web-security
```


## Build and start postcardsender

Install java, maven and node, if not present

Compile the application

    mvn clean install

Run the application 

    cd postcardsender-web
    mvn spring-boot:run -Dspring-boot.run.arguments=--api.post.client.id=XXXXXXXXXXXXXX,--api.post.client.secret=XXXXXXXXXXXXX
    (or just mvn spring-boot:run if you don't have api-credentials)
    
Manage your campaign on [http://localhost:8080/postcardsender-web/ui/campaign](http://localhost:8080/postcardsender-web/ui/campaign)
    
    default login = admin/admin
    
Create postcards on on [http://localhost:8080/postcardsender-web](http://localhost:8080/postcardsender-web)    
    
## Configure settings

You will find the a separate application.properties-file for every environment at ..\postcardsender-web\src\main\resources\config\ where you can configure your settings.

To be able to communicate with the [Postcard API](https://developer.post.ch/en/technical-specifications-of-postcard-api) of SWISS POST please set the OAuth2-credentials provided from SWISS POST.
    
    api.post.client.id=XXXXXXXXXXXXXX
    api.post.client.secret=XXXXXXXXXXXXX

Please also change all other passwords contained in the properties-files.


## Start client on separate port for testing an debugging Angular

Run the client on [http://localhost:4200](http://localhost:4200/postcardsender-web)

    cd postcardsender-webclient
    npm run startWithBackend
    


## Create our own webclient

If you want to implement your own web-client, please run the application and look at the 
[Swagger Gui at http://localhost:8080/postcardsender-web/swagger-ui.html](http://localhost:8080/postcardsender-web/swagger-ui.html) to get futher information about the backends REST API.

Please make sure to copy your webclient to /META-INF/resources/webjars/ at build-time.


## Used tools and frameworks

This project uses:
     
**Build & packagemanagement**
- Maven 3
- NodeJs 10 & NPM 6

**Backend (Java 8)**
- Spring Boot 2

**Frontend**
- Angular 7
- Angular CLI 7
- Bootstrap 3