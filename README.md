# URL Shortener Web Service
The project is written by @xyiDuan for building a simple URL shortener app, just like [bit.ly](https://bitly.com). It will provide HTTP-based RESTful APIs for managing URLs and redirecting a short URL to the original one.
To simplify the scenario, each short URL will be permanent, unique and not easy to be discoverable. 

## Running Requirements

The project is using the newest version of the [Play](https://www.playframework.com) Java framework and [SBT](https://www.scala-sbt.org) as a building tool. 
Besides that, the project is also using [MySQL Community Server 8.0.18](https://dev.mysql.com/downloads/mysql/) as the app's database.

STEP 1: Please follow MySQL's steps for installing the DB server, the password that I am using for the root user is `12345678`, but feel free to change it and also modify the value inside the `conf/application.conf` file.

STEP 2: Please access the MySQL in the terminal, if you are using Mac, you can use `/usr/local/mysql/bin/mysql -uroot -p`. Please run the script `db_schema.sql` first and then, go to step 3.

STEP 3: `cd path/to/project_folder` (The directory should have `build.sbt` inside).

STEP 4: Start the local server
> If you already had SBT installed in your laptop

Please just run `sbt run`, clean it if necessary

> If **not**, don't worry, you can directly use a script inside the project. 

Please use `./sbt run`. If permission is denied, please run `sudo chmod 755 'sbt'` and `sudo chmod 755 '/sbt-dist/bin/sbt'` and then, retry the command again.

If the server is up, you should see *"Server started, use Enter to stop and go back to the console..."* from the terminal.

STEP 5: Please go to the browser and enter `localhost:9000`, the main page should show *"Welcome to the URL Shortener App!"*.

## How to import the project to SBT
Please download the scala plugin first, restart the IntelliJ and then, import the project. Intellij will auto-sync for you.

## API Introduction and Test
Please take a look at `conf/routes` and `app/controllers/HomeController.class` for details. This project hasn't implemented the UI and the user authentication part, will add them to the future plan.
##### Generating a short URL from a long URL 
Here is an **example** for using POST API `/generate-short-url`:
```$xslt
curl -X POST -H "Content-Type: application/json" \
-d '{"url":"https://www.youtube.com"}' \
http://localhost:9000/generate-short-url
```
We will get json output:
```$xslt
{
    "longURL":"https://www.youtube.com",
    "shortURL":"http://localhost:9000/dMc",
    "createdDateTime":"10/19/2019 17:48:25"
}
```
By using this API, youtube.com is mapped to a shortURL which is `{domain}/dMc`

##### Redirect a short url to the long url
After getting the generated short url, you can go to the browser and enter `http://localhost:9000/<short-url-key>`, you will be redirected to the long url page automatically.

Following up on the first example, we already had a short URL for youtube.com, you can test the speed by using:
```$xslt
time curl -X GET http://localhost:9000/dMc
```
The total time we use for redirecting is less than 6ms. Enter `http://localhost:9000/dMc` in a browser, you will be automatically redirected to Youtube.

##### The number of times a short URL has been accessed
Please see the example in here, it is a very simple log analysis based on the url_log table. More analysis could be added later. 
```$xslt
curl -w GET http://localhost:9000/get-log-analysis/zhX
```
`zhX` is a short string we generated for a long URL, the response we get is:
```$xslt
{
    "shortURL":"http://localhost:9000/zhX",
    "longURL":"https://www.google.com",
    "timesWithin24Hours":11,
    "timesWithin7Days":11,
    "timesWithinAllTime":11
}
```

##### Unit Test For the APIs
Please check the test folder for running the unit test cases.

## Design Decisions Explanation
In this part, I will talk about the algorithm design for creating shorter alias on long URLs, DB design for storage and the trade-offs I made during the process.

Here are my thoughts for URL shortening:
- URL redirection should have minimal latency 
- The short URL should not be predicable easily by other people. 
- The whole system should be read-heavy

##### Algorithm Design for generating short URLs
We could have multiple methods for generating short URLs.
1) Encode long URLs by using MD5 and SHA256 directly and base62/base64 transform encoded string. Base62/base64 generate 6 letters long key (64^6) that could represent billions of URLs.
However, this method could result in key duplication as the same long url map to the same short url. Also, we need to query via the short URL and it would not be as fast as querying via auto-incremented id.
2) Use Random numbers directly. We cannot decode the short URL if we directly rely on randomized numbers. By applying it to millions of URLs, no uniqueness can be guaranteed also. If we use **GUID** for inserting a new URL into tables, it will also be costly if the system is large.

So, here is my solution: 

Use an auto-incremental primary key ID for a long URL and encode the ID for getting the short URL. To ensure the unpredictability and uniqueness further, created_datetime is combined with ID for encoding also. 
The base62Map is also randomized for enhancing privacy. By using this method, 
the short URL is not easy to be discoverable and we can decode the short URL for getting long URL fast. 
We definitely have more security jobs to do for this project also.

The sequential ID would be convenient for the future data sharding also.
 We will need machine-id contributed to the short-url encoding if multiple machines are storing the data. 
 We will need size 2 for encoding created_datetime and size 6 for encoding the ID. Therefore, 
 we should have `shortURLString = encode(created_datetime) + encode(ID)`

##### Database Schema
Our service will be read-heavy with a simple data structure. Two tables are needed in here, we should have a user_master table in the future also.

Table 1 - `url_profile` for storing the mapping between short_url and long_url

|Column|Type|Constraint|      
|-----------|-----------|-----------|
|id|Long(Auto-Increment)|Primary Key|
|short_url|String(10) |Unique|
|long_url|String|Not Null|
|created_datetime|Datetime|Not Null|

Table 2 - `url_log` for logging the short url access times

|Column|Type|Constraint|      
|-----------|-----------|-----------|
|id|Long(Auto-Increment)|Not Null|
|short_url|String(10) |Not Null|
|created_datetime|Datetime|Not Null|
Need create index for id and short_url also. 

The column id is auto-incremental in the table. I will decode the id for getting the shorter alias on based on the algorithm above. 

##### Project structure
For understanding the code structure, please start with `app/shortener`

`URLService.class` contains the main logic for different APIs.

Go to the `app/view`

`index.scala.html` would be the place for building the Main page UI.


##### How to scale the app
Data Partition (Consistent Hashing) + Replication + Cache + Load Balancer

## Future Works
1. Build UI
2. Long URL validation, if the user gives an invalid long url, we should alert the user. The whole app should be more user-friendly.
3. User **authentication** and security. We need to build a user management mechanism for this app, after having user keys, we can generate short URLs with user keys together. It will enhance the privacy of short urls further. On the other hand, The user login mechanism will also prevent API abuse.  
4. Handle different types of exceptions if possible
5. More log analysis for short urls like the top frequent URLs, then, we should build a **cache** to store the most frequently accessed URLs.
6. More unit test cases for edge scenarios.
7. Feature for allowing users to create **customized** URLs.
8. DELETE API for short URLs.

