// ==UserScript==
// @name         EAC Remote Login
// @namespace    https://bauxe.dev/
// @version      2025-04-25
// @description  login to a remote eac client using eac-tk
// @author       Bauxe
// @match        https://p.eagate.573.jp/game/konasteapp/API/login/login.html*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=573.jp
// @grant        GM_getValue
// @grant        GM_setValue
// ==/UserScript==

(function() {
    'use strict';
    function waitForLoginButton(callback) {
        const interval = setInterval(() => {
            const loginButton = document.querySelector("#login > div > button");
            if (loginButton) {
                clearInterval(interval);
                callback(loginButton);
            }
        }, 100);
    }

    waitForLoginButton((loginButton) => {
        // Create input for remote host
        const input = document.createElement("input");
        input.placeholder = "127.0.0.1";
        input.style.margin = "10px";
        input.id = "remoteHostInput";
        input.value = GM_getValue('remoteHost', '127.0.0.1');

        // Create remote login button
        const button = document.createElement("button");
        button.textContent = "Remote Login";
        button.style.margin = "10px";

        // Insert into the DOM
        const container = loginButton.parentElement;
        container.appendChild(document.createElement("br"));
        container.appendChild(input);
        container.appendChild(button);

        button.onclick = () => {
            const onclickStr = loginButton.onclick.toString();
            const match = onclickStr.match(/tk=([a-zA-Z0-9\-]+)/);
            const token = match ? match[1] : null;
            const host = document.getElementById("remoteHostInput").value;

            GM_setValue('remoteHost', host);

            if (!host) {
                alert("Please enter a remote host.");
                return;
            }

            if (!token) {
                alert("Token not found in login button.");
                return;
            }

            const url = `http://${host}:44444/tk/${token}`;
            window.location.href = url;
        };
    });
    // Your code here...
})();