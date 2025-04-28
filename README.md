# eac-tk

Generate an EAC ticket (only for SDVX at this stage)

## Usage

### Installation
1. Grab the [latest release](https://github.com/konaste-tools/eac-tk/releases) as well as the [start script](https://raw.githubusercontent.com/konaste-tools/eac-tk/refs/heads/main/bin/start.bat). Place these in the same location on your computer that will be running Sound Voltex Konaste.
2. **(optional)** if your game is not installed in the default location **(C:\Games\SOUND VOLTEX EXCEED GEAR\)**, edit the start script to point to the right directory.
3. **(optional)** adjust your cabinet's startup script to run `start.bat` instead of the konaste laucher.
4. Note down the IP address of your cabinet - you can find this by typing `ipconfig` into a command prompt window. It should look something like 192.168.1.50.

### Remote Login from a computer
1. Install [TamperMonkey](https://www.tampermonkey.net/) on your browser of choice. If you are using a relatively new Chrome version, you will need to [enable developer mode](https://www.tampermonkey.net/faq.php#Q209).
2. Create a new script, using [eac-remote-login.js](https://raw.githubusercontent.com/konaste-tools/eac-tk/refs/heads/main/bin/eac-remote-login.js) as the script source.
3. Navigate to https://p.eagate.573.jp/game/konasteapp/API/login/login.html?game_id=sdvx
4. Enter the cabinet's IP address in the remote login textbox and click Remote Login.

![Login Display](images/login.png)

An example video can be found [here](https://youtu.be/QgOEXaFhIgo).

### Remote Login from an IOS device
1. Install the [konaste-remote-login shortcut](https://github.com/konaste-tools/eac-tk/raw/refs/heads/main/bin/konaste-remote-login.shortcut).
2. Enable running scripts from shortcuts.
   1. Open Settings
   2. Apps > Shortcuts > Advanced > enable Allow Running Scripts
3. Navigate to https://p.eagate.573.jp/game/konasteapp/API/login/login.html?game_id=sdvx
4. Press the share button [^], scroll down and tap on the `konaste-remote-login` action.
5. In the input box that  appears, enter the IP address of the cabinet and hit Done. You may need to accept some permissions.

### Remote Login from an Android device
to be added

### Troubleshooting
For these steps, **primary device** refers to the computer running Sound Voltex / eac-tk and **other device** refers to any other device.

> I am unable to connect to the server / I am receiving an error when I try to remotely login
1. Verify you are using the correct remote-ip - you can obtain this by typing `ipconfig` in a command prompt window on the primary device.
2. Ensure `eac-tk.exe` is running - you should observe a blank command prompt window on the primary device.
2. On the primary device, try navigating to `http://localhost:44444/tk/test` in a browser. If the `eac-tk.exe` command prompt window closes, you are able to connect.
3. Repeat the same on your other device, instead navigating to `http://{remote-ip}:44444/tk/test`. Similarly, if the command prompt window closes, you are able to connect.
4. Open port 44444 on the primary device. You can use the below script to achieve this.
```
New-NetFirewallRule -DisplayName "eac-tk Port 44444" -Direction inbound -Profile Any -Action Allow -LocalPort 44444 -Protocol TCP
Enable-NetFirewallRule -DisplayName "eac-tk Port 44444"
netstat -an | findstr 44444
```
You will observe a response `TCP    0.0.0.0:44444          0.0.0.0:0              LISTENING`
5. Verify that your other device can connect, by executing the following in a command prompt window.
```
Test-NetConnection -ComputerName 192.168.8.119 -Port 44444
```

### Advanced Usage

Use the executable however you would like - add the following snippet anywhere in a batch script to fetch a token: 
`FOR /F %%i IN ('cmd /C "set KTOR_LOG_LEVEL=ERROR && eac-tk"') DO set TOKEN=%%i`

On a separate machine, navigate to {local-ip}:44444/tk/{tk}
The ticket can be found on the game boot page with inspect element or by using the tampermonkey script provided.
