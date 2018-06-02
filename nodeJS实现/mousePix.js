// 沃日。。mac上的管理员用户（sudo）和系统用户（最高权限）是两个不同的用户。。。


const {
    clipboard,
    ipcRenderer,
    remote
} = require('electron');
const win = remote.getCurrentWindow(); //渲染进程访问主进程
const robot = require('robotjs');
let pos = document.querySelector('#pos');
let color = document.querySelector('#color');
// let log = document.querySelector('#log');

function exit() {
    win.removeAllListeners('close');
    win.close();
    remote.app.quit();
}

function copy(text) {
    clipboard.writeText(text);
    let log = document.createElement('div')
    document.body.appendChild(log)
    log.className = 'log';
    log.innerHTML = 'Copied'
    setTimeout(() => { //CSS渲染要滞后于JS
        // log.classList.remove('fadeOut');
        document.body.removeChild(log);
        // log.remove();
    }, 3000);
}


let timer = () => setInterval(() => {
    let { x, y } = robot.getMousePos();
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
                document.title = 'PixelPicker ( paused )'
            } else {
                key = timer();
                document.title = '像素拾取神器'
            }
            break;
        case 27: //esc
            ipcRenderer.send('exit');
            // win.close(); //第二种方法
            break;
        default:
            break;
    }
});