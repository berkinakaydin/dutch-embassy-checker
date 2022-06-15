# Dutch Embassy Checker

This repository contains of a Selenium based automation project. It simply goes to the website of the Dutch Embassy and try to figure out if there is any available date to have an appointment. If there is an available appointment date, application sends an email to desired person. 

##  Before Starting

* Clone the repository:
```
$ git clone https://github.com/berkinakaydin/dutch-embassy-checker.git && cd "$(basename "$_" .git)"
```

* Download Google Chrome
* Download Google Chrome Webdriver
https://chromedriver.chromium.org/downloads
Download proper version and architecture. **Place it under app/bin folder.**

* Create a file called .env on the root folder
* Create application password if gmail is selected for sending emails. Do the similar for other services. You will need it on the environment variables section.

### Envrionment Variables
 
```text
# sender email
SENDER=<sender>
# password of email sender to create mail session
PASSWORD=<password>
# receiver email
RECEIVER=<receiver>
# desired services host smtp.gmail.com for gmail
HOST=<host>
# mail sender port, 465 for SMTP
PORT=<port>
# Only MR. or MRS. or MS. for Applicant Details Page
TITLE=<title>
# for Applicant Details Page
NAME=<name>
# for Applicant Details Page
SURNAME=<surname>
# for Applicant Details Page
PHONE=<phone>
# for Applicant Details Page
EMAIL=<email>
# Run application in headless mode. Default false
HEADLESS=<true> | <false>
```

## Usage

Next, run the following commands to package as an artifact. 
Note=Maven copies the artifact to the root folder.
```bash
./mvnw clean package
```

Now you can run it with
```bash
java -jar <artifact_name.jar>
```

## Notes
If there is a suitable date, the application only sends email. It does not apply by itself.

You may want to deploy it into a VPS as a cron job. Here is how you can do

* Edit crontab with command below
```bash
crontab -e
```

* Create a folder to observe logs 
```bash
mkdir logs
```

* Add your job as a cron job (*/5 means, runs the job for every 5 mins)
```bash
*/5 * * * * java -jar <artifact>.jar >> /home/<user>/logs/daily-backup.log 2>&1
```

## License
[MIT](https://choosealicense.com/licenses/mit/)

<p align="center">
  :heart=Selenium :heart:
</p>
