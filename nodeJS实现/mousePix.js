const {clipboard,ipcRenderer,remote} = require('electron');
const win = remote.getCurrentWindow();  //渲染进程访问主进程
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
                document.title = 'PixInfo ( paused )'
            } else {
                key = timer();
                document.title = 'PixInfo'
            }
            break;
        case 27:    //esc
            ipcRenderer.send('exit');
            // win.close(); //第二种方法
            break;
        default:
            break;
    }
});