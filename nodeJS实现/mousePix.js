const win = require('electron').remote.getCurrentWindow();  //渲染进程访问主进程
const {clipboard} = require('electron');
const robot = require('robotjs');
let pos = document.querySelector('#pos');
let color = document.querySelector('#color');
let log = document.querySelector('#log');

function exit() {
    win.removeAllListeners('close');
    win.close();
    remote.app.quit();
}

function copy(text) {
    clipboard.writeText(text);
    log.className = 'fadeOut';
    setTimeout(() => {  //CSS渲染要滞后于JS
        log.classList.remove('fadeOut');
    }, 3000)
}


let timer = () => setInterval(() => {
    let {x, y} = robot.getMousePos();
    pos.innerHTML = `${x},${y}`;
    color.innerHTML = '#' + robot.getPixelColor(x, y);
}, 30);

let key = timer();

addEventListener('keydown', (e) => {
    switch (e.keyCode) {
        case 32:
            if (key) {
                clearInterval(key);
                key = null;
            } else key = timer();
            break;
        default:
            break;
    }
});