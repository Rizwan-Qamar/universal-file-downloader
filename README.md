# Universal File Downloader
This is universal file downloader that can download URLS with HTTP and FTP protocol. It can be easily extended by extending the **AbstractFileHandler** class.

## Feature

> Support FTP, HTTP, HTTPS protocols

> Download location can be modified using properties file


## Technical Requirements
In order to execute the **UniversalFileDownloader(UFD)** you need to have **JDK 8** installed.

## Architecture Overview
The `universalFileDownloader` has 3 artifacts:
###`api` 
It is responsible for downloading the file and saving it to the disk. New protocols can also be added in this artifact.

### `core` 
It is responsible for the business logic and threading
 
### `repository` 
It is responsible for interacting with the database

## Configurable Properties
`ufd.core.api.downloadLocation` can be used to specify the downloading location. It is a relative path and files will be downloaded within the project directory.

`ufd.core.api.generic.username` is used only for FTP protocol. In order to access public FTP remove this property from the properties file.

`ufd.core.api.generic.password` is used only for FTP protocol. In order to access public FTP remove this property from the properties file.


## How to install UniversalFileDownloader
After cloning the repository, execute **_"gradlew.bat build"_** command from the root directory of the project (**_./gradlew build for Linux users_**).

## How to use UniversalFileDownloader

### Dashboard
UFD provides an easy to use dashboard. After you complete the starting process, the dashboard should be available on port **8080**. Therefore, connecting to [http://localhost:8080] you should be able to access the dashboard.

A sample file name **"Sample-Requests.txt"** can be used for testing the application. It is located in `src->main->resources` of the main project.