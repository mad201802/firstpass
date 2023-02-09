
#FirstPass
The best Password Manager in the World!

FirstPass is designed to allow users to store their passwords securely and easily. Among other things, we offer a password generator that automatically generates secure passwords. This is seamlessly integrated into the manager and can be configured as desired. But not only username and password can be stored: It is also possible to save a URL to the website to access it more easily. It is also possible to leave a note, which is of course also encrypted. The security of the data has the highest priority for us!

  - [Features](#features)
  - [Build from Source](#build-from-source)
  - [Installation](#installation)
    - [Windows](#windows)
    - [Mac](#mac)
    - [Linux](#linux)
  - [Usage](#usage)
  - [Contributing](#contributing)
  - [Copyright](#copyright)


##Features

 - _Encrypted storage_ of passwords using AES-256 encryption
 - Automatically _generates strong passwords_
 - Ability to store and manage passwords for _multiple accounts and websites_
 - _Cross-platform compatibility_ (Windows, Mac, Linux)
 - User-friendly interface including _customizable Themes_

##Build from source

In order to build FirstPass from source you need to have Java 17 installed, as well as npm.
1.	Build the backend project in the backend folder with `mvn package`
2.	Navigate into the frontend folder and execute `npm install` (ignore the security warnings for now)
3.	Start the frontend build with `npm run dev`

##Installation
To install the password manager, follow the instructions for your platform:

###Windows

1. Download the `Firstpass.Setup.1.1.1.exe`
   file from the releases page.
2. Double-click the file to start the installation process.
3. Follow the on-screen instructions to complete the installation.

###Mac

1. Download the `Firstpass-1.1.1.dmg`
   file from the releases page.
2. Double-click the file to mount the disk image.
3. Drag the application to your Applications folder.
4. Double-click the application to start it.

###Linux

1. Download the `firstpass-1.1.1.tar.gz`
   file from the releases page.
2. Open a terminal and navigate to the directory where you downloaded the package.
3. Extract the contents of the downloaded file by running the following command:<br>
   `tar -xzvf firstpass-1.1.1.tar.gz`
4. Navigate into the extracted directory and run the Installation script:<br>
`./configure` <br>
`make`<br>
`sudo make install`


##Usage

- _**Start the password manager**_ and create a new database or log in to an existing database.
- Add a _**new password**_ by clicking the "New Password" button and entering the required information.
- _**Add, edit or delete a category**_ by clicking the three buttons at the top of the sidebar
- You can also **_generate a strong password_** by clicking the button next to the password input field
- To **_update, delete or show a password_**, just click on its entry in the password list
- To **_move a password_** in a different category, just drag and drop it
- To **_add a new Theme_**, go to the settings menu and clone an existing Theme or load a JSON-File in the right format


##Contributing
Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.
Please make sure to update tests as appropriate.

##Copyright
FirstPass (c) 2022 Alex Bossert, Antonia Herdtner, Leonard Laisé, Luca von Kannen, Maurice Dolibois, Michael Dick, Tom Flocken
SPDX-License-Identifier: GPL-3.0

