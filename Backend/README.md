# LiveTutorial2021 Backend Realm App

## Create Atlas Cluster

- Navigate to the [Atlas UI](https://cloud.mongodb.com) UI.
- If you don't already have one, create a MongoDB cloud account.
- Create a free-tier M0 Atlas Cluster (you can use an existing Atlas cluster if you have one)

## Create MongoDB Realm App

- Click on the Realm icon to switch to the MongoDB Realm UI:

![image](https://user-images.githubusercontent.com/14197449/126492789-83b915bc-ff6e-4c90-85b5-4489fb857c97.png)

- Create a new Realm app:

![image](https://user-images.githubusercontent.com/14197449/126493174-f7c6fbdf-722e-4f7e-93ee-6d288e02e8b2.png)

- Note the application ID – you'll need to add this to your mobile apps:

![image](https://user-images.githubusercontent.com/14197449/126493275-e342bd04-1e59-4ad2-acb8-93ae3e5aca68.png)

### Enable Email/Password Authentication

- Select "Authentication":

![image](https://user-images.githubusercontent.com/14197449/126493390-5a3fe6f2-fe30-4f79-8b10-4f7a49fb9445.png)

- Click on "EDIT" for the email/password authentication provider:

![image](https://user-images.githubusercontent.com/14197449/126493570-945ebb15-eb33-4804-bd83-7469b2a8bb1f.png)

- Turn on the provider
- Select "Automatically confirm users"
- Select "Run a password reset function"
- Select "+ New Function"; you can leave the function code as it is
- Click "Save Draft"

![image](https://user-images.githubusercontent.com/14197449/126493774-2882f64e-1965-47c2-95bd-c1121f7741a7.png)

### Enable MongoDB Realm Sync

- Select "Sync" from the left menu:

![image](https://user-images.githubusercontent.com/14197449/126494255-89721f1e-7d34-4fd0-9f44-dda71e6af98b.png)

- Select "Define Data Models"

![image](https://user-images.githubusercontent.com/14197449/126494303-61c97d56-06cf-4079-8217-eb42bb4b7b4e.png)

- Select your Atlas cluster
- Specify "room" as the partition key
- Select "string" as the key type
- Set the database name to LiveChat
- Click "Turn Dev Mode On"

### Deploy App

- Click on "REVIEW DRAFT & DEPLOY"

![image](https://user-images.githubusercontent.com/14197449/126494130-03e4600c-0ee9-45b4-af6b-4ff7d93d945d.png)

- Accept the changes. Your app is now live.
