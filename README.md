<h1 align="center">Trending Repositories</h1>
<div align="center">

[![Badge](https://img.shields.io/badge/language-Kotlin-purple)](https://kotlinlang.com)
[![Badge](https://wakatime.com/badge/user/c5c7cf00-dda5-4401-be38-f4dbcbb087f1/project/ebd0f8da-5fea-4629-a030-1c1dade8f773.svg)](https://wakatime.com/badge/user/c5c7cf00-dda5-4401-be38-f4dbcbb087f1/project/ebd0f8da-5fea-4629-a030-1c1dade8f773)
![Badge](https://img.shields.io/badge/built_with-love-red)
![Badge](https://img.shields.io/badge/GitHub-Trending-brightgreen)
![Badge](https://img.shields.io/badge/uses-Telegram%20Bot%20API-blue)
![Badge](https://img.shields.io/badge/coverage-100%25-green)
![Badge](https://img.shields.io/badge/CI/CD-GitHub%20Actions-orange)
![Badge](https://img.shields.io/badge/build-gradle-red)
![Badge](https://img.shields.io/badge/tests-JUnit-blue)
![Badge](https://img.shields.io/badge/mocks-Mockito-yellow)
![Badge](https://img.shields.io/badge/license-MIT-brightgreen)

<img src="./images/banner.png" alt="Banner">
</div>

## Overview
I have created a bot that does two things. First, it collects <a href="https://github.com/trending" >trending repositories</a> from GitHub and sends them to a channel.
The details of the repositories, such as their names, star counts, forks, etc., are also provided. Second, I display a chart based on the programming languages used 
in these trending repositories. Every day, my code runs using `GitHub actions` and automatically sends the content in the channel. 
This is the link to the channel: <a href="https://t.me/github_trending_channel" target="_blank">t.me/github_trending_channel</a>

Happy coding and stay updated with the latest trending repositories! 🚀

⚠️ Because the current character limit for messages on Telegram is `4096`, I can't show all the trending repos in the channel. Therefore, I've removed some repos.

## Running the Project
To run the project, first, get your Telegram bot token. Then, in your project folder, run this command:

```gradle
./gradlew run --args=botToken
```
Replace `botToken` with your actual Telegram bot token. 
For more details, please refer to the Telegram api documentation.






⚠️ This command works for me on Windows. If you're using Linux or macOS, please modify it accordingly for your platform if needed.

An alternate approach to run the project involves utilizing program arguments through the "Edit Configuration" feature. If you've acquired your Telegram bot token, you can insert it as an argument following these steps:
1. Open intellij idea and navigate to the "Edit Configurations" settings.
2. In the "Program arguments" field, input your Telegram bot token.
3. Save the configuration settings.
4. Click the green "Run" button provided by the IDE.

## Credits
- <a href="https://github.com/alisoft/github-trending-api"> Github Trending Api </a>

- <a href="https://quickchart.io"> QuickChart </a>
                                                
## Contributing
Contributions to the Trending Repositories repo are welcome! If you encounter issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## License
This repository is licensed under the [MIT License](https://choosealicense.com/licenses/mit/).
