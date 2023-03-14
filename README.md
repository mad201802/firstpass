[![Package Frontend and backend](https://img.shields.io/github/actions/workflow/status/mad201802/firstpass/package.yaml?logo=github)](https://github.com/mad201802/firstpass/actions/workflows/package.yaml)
[![Backend Tests](https://img.shields.io/github/actions/workflow/status/mad201802/firstpass/pullrequest_backend.yaml?label=backend%20tests&logo=github)](https://github.com/mad201802/firstpass/actions/workflows/pullrequest_backend.yaml)
[![Frontend Tests](https://img.shields.io/github/actions/workflow/status/mad201802/firstpass/pullrequest_frontend.yaml?label=frontend%20tests&logo=github)](https://github.com/mad201802/firstpass/actions/workflows/frontend_backend.yaml) \
[![License](https://img.shields.io/github/license/mad201802/firstpass?color=informational)](https://github.com/mad201802/firstpass/blob/main/LICENSE)
[![Latest Release](https://badgen.net/github/release/mad201802/firstpass)](https://github.com/mad201802/firstpass/releases/latest)
[![Open Issues](https://img.shields.io/github/issues-raw/mad201802/firstpass?color=orange&logo=github)](https://github.com/mad201802/firstpass/issues)
[![Commits](https://badgen.net/github/commits/mad201802/firstpass/main)](https://github.com/mad201802/firstpass/commits)
[![Pull Requests](https://badgen.net/github/prs/mad201802/firstpass)](https://github.com/mad201802/firstpass/pulls) \
![amazing](https://img.shields.io/badge/amazing-yes-blueviolet)
![fast](https://img.shields.io/badge/lightning-fast-blueviolet)
![secure](https://img.shields.io/badge/secure-very-blueviolet)

<br>

# FirstPass <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Key.png" alt="Key" width="30" height="30" style="transform: translate(0px, 5px)" />
**The best Password Manager in the World!** <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Exploding%20Head.png" alt="Exploding Head" width="22" height="22" style="transform: translateY(5px)" />

FirstPass is designed to allow users to store their passwords securely and easily. Among other things, we offer a password generator that automatically generates secure passwords. This is seamlessly integrated into the manager and can be configured as desired. But not only username and password can be stored: It is also possible to save a URL to the website to access it more easily. It is also possible to leave a note, which is of course also encrypted. The security of the data has the highest priority for us!

  - [Features](#features)
  - [Installation](#installation)
    - [Windows](#windows)
    - [Mac](#mac)
    - [Linux](#linux)
  - [Usage](#usage)
  - [Build from Source](#build-from-source)
  - [Contributing](#contributing)
  - [Copyright](#copyright)

<br>

# Attention!
No Security Audit was accomplished. **Please do not use Firstpass as your real world password manager.**

<br>

## Features <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Star-Struck.png" alt="Star-Struck" width="25" height="25" style="transform: translateY(5px)" />


 - _Encrypted storage_ of passwords using AES-256 encryption
 - Automatically _generates strong passwords_
 - Ability to store and manage passwords for _multiple accounts and websites_
 - _Cross-platform compatibility_ (Windows, Mac, Linux)
 - User-friendly interface including _customizable Themes_

<br>

## Installation <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Floppy%20Disk.png" alt="Floppy Disk" width="25" height="25" style="transform: translateY(5px)" />

To install the password manager, follow the instructions for your platform. \
Make sure you have the Java 17 JRE installed.

### Windows

1. Download the `Firstpass Setup X.X.X.exe` file from the releases page.
2. Double-click the file to start the installation process.
3. Follow the on-screen instructions to complete the installation.

### Mac

1. Download the `Firstpass-X.X.X.dmg` file from the releases page.
2. Run the file to start the installation process.
4. Follow the on-screen instructions to complete the installation.


### Linux

1. Download the `Firstpass-X.X.X.AppImage` file from the releases page.
2. You may need to run `chmod +x Firstpass-X.X.X.AppImage`.	
3. Run the file: `./Firstpass-X.X.X.AppImage`

### Archive Install
For every platform we also provide an archive containing all program files. To install using an archive, download the right one for your platform:
- Windows: `Firstpass-X.X.X-win.zip`
- Mac: `Firstpass-X.X.X-mac.tar.gz`
- Linux: `Firstpass-X.X.X.tar.gz`

After downloading the archive, extract it to a folder of your choice. Then, run the `Firstpass` executable inside.

<br>

## Usage <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Electric%20Plug.png" alt="Electric Plug" width="25" height="25" style="transform: translateY(5px)" />

- _**Start the password manager**_ and create a new database or log in to an existing database.
- Add a _**new password**_ by clicking the "Add" button and entering the required information.
- _**Add, edit or delete a category**_ by clicking the three buttons at the top of the sidebar
- You can also **_generate a strong password_** by clicking the button next to the password input field
- To **_update, delete or show a password_**, just click on its entry in the password list
- To **_move a password_** in a different category, just drag and drop it
- To **_add a new Theme_**, go to the settings menu and clone an existing Theme or load a JSON-File in the right format

<br>

## Build from source <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Hammer%20and%20Wrench.png" alt="Hammer and Wrench" width="25" height="25" style="transform: translateY(5px)" />


In order to build FirstPass from source you need to have [NodeJS](https://nodejs.org/), [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) & [Maven](https://maven.apache.org/), npm & [yarn](https://yarnpkg.com/). \
To build, navigate to the project directory and run:
```bash
npm run dist    # builds backend, frontend and packages the app
```
**Note:** On Mac, you need to run `export CSC_IDENTITY_AUTO_DISCOVERY=false` first, to prevent the build from trying to do code signing. \
This will build the backend and the frontend and package it into a distributable package for you platform. \
The output will be in the `dist` folder.

<br>

## Troubleshooting <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Screwdriver.png" alt="Screwdriver" width="25" height="25" style="transform: translateY(5px)" />

In case you encounter any problems, feel free to contact us via [email](md148@hdm-stuttgart.de) or open an [issue](https://github.com/mad201802/firstpass/issues/new/choose) on GitHub.

<br>

## Contributing <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Hand%20gestures/Handshake.png" alt="Handshake" width="25" height="25" style="transform: translateY(5px)" />

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.
Please make sure to update tests accordingly.

<br>

## Copyright <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Page%20with%20Curl.png" alt="Page with Curl" width="25" height="25" style="transform: translateY(5px)" />

FirstPass (c) 2023
[Alexander Bossert](mailto:ab306@hdm-stuttgart.de),
[Antonia Herdtner](mailto:ah247@hdm-stuttgart.de),
[Leonard Laisé](mailto:ll071@hdm-stuttgart.de),
[Luca von Kannen](mailto:lv042@hdm-stuttgart.de),
[Maurice Dolibois](mailto:md147@hdm-stuttgart.de),
[Michael Dick](mailto:md148@hdm-stuttgart.de),
[Tom Flocken](mailto:tf054@hdm-stuttgart.de) \
SPDX-License-Identifier: GPL-3.0
