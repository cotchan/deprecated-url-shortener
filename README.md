### url-shortener

+ Service Description

    API service to generate short URL

+ Service URL: ~~http://3.34.166.160~~

+ API Service Summary
    + ##### REST API
    
        ```
        POST /encode -> A short Url is generated for the received Url.
        GET /decode/:shortUrl -> A UrlDTO Information is provided for the received Url.
        GET /:shortUrl -> If you enter the short Url, you are redirected to the original Url page.
        ```
+ How to make
    + Server: Ubuntu 18.04 (AWS)
    + Web-Server: Nginx
    + API Server: Springboot 2.3.2
    + DB: Mysql, Redis

+ Redirection sample 

    ```
    1. www.kakao.com: http://3.34.166.160/f7Z3G2yWexF 
    2. www.toss.im: http://3.34.166.160/hHFY14UWV5F
    ```

---

### Contents

1. API    
    1-1. UserLevel API    
    1-2. SystemLevel API    

2. Overall structure design    

3. Class Diagram    

4. DB schema design

5. Data Flow Diagram
    
---

### 1. API

#### 1-1. User Level API

#### REST API
```
POST /encode -> A short Url is generated for the received Url.
GET /decode/:shortUrl -> A UrlDTO Information is provided for the received Url.
GET /:shortUrl -> If you enter the short Url, you are redirected to the original Url page.
```

---

<code> POST /encode </code>: A short Url is generated for the received Url.

Request Body
```
{
"url": String
}
```
Success case (Response Body)
```
statusCode: 200
{
"shortUrl": String
}
```

Fail case (Response Body)

```
statusCode: 200
{
}
```

<code>GET /decode/:shortUrl</code>: A UrlDTO Information is provided for the received Url.

Request Body
```
None
```

Success case (Response Body)

```
statusCode: 200
{
"uuid": Long,
"shortUrl": String,
"originalUrl": String
}
```

Fail case (Response Body)

```
statusCode: 404
{
}
```

<code>GET /:shortUrl</code>: If you enter the short Url, you are redirected to the original Url page.   

Request Body   

```
None
```
Success case (Response Body)
```
statusCode: 302 (Found)
{
}
```
Fail case (Response Body)
```
statusCode: 404
{
}
```

---

#### 1-2. System Level API    

* Contents
    1. DTO
    2. UrlStorageService.java
    3. UrlEncodeService.java 
    4. UrlDecodeService.java 
    5. UrlEncodeController.java 
    6. UrlDecodeController.java 
    7. UrlRouterController.java

#### 1. DTO

```
/**
 * Url DTO
 */
class Url { 
    private long uuid;
    private String shortUrl;
    private String originalUrl;
}
```

#### 2. UrlStorageService.java  
UrlRepository class

```
Url getByOriginalUrl(String originalUrl);
Url getByShortUrl(String shortUrl);
CompletableFuture<Boolean> saveUrl(Url url);
```
    
#### 3. UrlEncodeService.java 
Class to make shortUrl from OriginalUrl

```
/**
 * Method to make shortUrl from OriginalUrl
 * @param originalUrl
 * @return shortUrl
 */
public CompletableFuture<String> encodeUrl(final String requestedUrl)
```

#### 4. UrlDecodeService.java 
Class to get UrlDTO information from shortUrl

```
/**
 * Method to get UrlDTO information from shortUrl
 * @param shortUrl
 * @return Url
 */
public Url decodeUrl(final String requestedUrl);
```

#### 5. UrlEncodeController.java
Class that handles "A short Url is generated for the received Url." request

```
/**
 * Method that handles "A short Url is generated for the received Url." request
 * @param requestBody
 * @return shortUrl
 */
public CompletableFuture<String> encodeUrl(@RequestBody String requestBody)
```

#### 6. UrlDecodeController.java 
Class that handles "A UrlDTO Information is provided for the received Url." request

```
/**
 * Method that handles "A UrlDTO Information is provided for the received Url." request
 * @param shortUrl
 * @return HttpResponse
 */
public HttpResponse decodeUrl(@Param String url)
```

#### 7. UrlRouterController.java
Class that handles "If you enter the short Url, you are redirected to the original Url page." request

```
/**
 * Method to redirect to originalUrl page
 * @param shortUrl
 * @return HttpResponse
 */
public HttpResponse routeUsingShortUrl(@Param String url)
```

---

### 2. Overall structure design    

![overall_design](https://user-images.githubusercontent.com/44143312/88477531-c8963380-cf0e-11ea-896f-770c348debf3.jpg)

---

### 3. Class Diagram

![ClassDiagram (2)](https://user-images.githubusercontent.com/44143312/88477041-c7fb9e00-cf0a-11ea-9fce-1a978128db91.jpg)

--- 

### 4. DB schema design

|Field|Type|Null|Key|Default Value|DESC|
|------|---|---|---|---|---|
|uuid|bigint(20)|NO|PK|NULL|Unique key for generating shortUrl|
|original_url|varchar(255)|NO|UNIQUE|NULL|OriginalUrl|
|short_url|varchar(255)|NO||NULL|ShortUrl created from originalUrl|


---

### 5. Data Flow Diagram

![dataFlowDiagram](https://user-images.githubusercontent.com/44143312/88478696-6593b380-cf85-11ea-8110-002b201899ca.jpg)



