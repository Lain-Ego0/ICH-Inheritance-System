import store from "@/store";
import router from '@/router'

import { Notification } from 'element-ui';
let wsObj = null;

let wbSocket = {
    isConnectState: false,
    onMessageMonitorList: []
};



/**
 * 添加消息监听
 * @param {Object} id 唯一标识
 * @param {Object} callback 回调方法
 */
export function AddSocketMessageMonitor(id, callback) {
    console.log("添加消息监听id:" + id)
    wbSocket.onMessageMonitorList.push({
        id: id,
        callback: callback
    })
}
/**
 * 移除消息监听
 * @param {Object} id 唯一标识
 */
export function RemoveSocketMessageMonitor(id) {
    console.log("移除消息监听id:" + id)
    wbSocket.onMessageMonitorList = wbSocket.onMessageMonitorList.filter(x => x.id != id);

}

//初始化websocket
export async function RetryConnect(timeout = 2000) {
    if (wbSocket.isConnectState) {
        return;
    }


    setTimeout(async () => {
        if (store.getters.UserId) {
            console.log("执行开始连接");
            const wsUrl = process.env.VUE_APP_BASE_WS + "/webscoket/" + store.getters.UserId;
            wsObj = new WebSocket(wsUrl);

            wsObj.onmessage = (res) => {
                console.log("接受到消息并且分发");
                wbSocket.onMessageMonitorList.forEach((monitor) => {
                    let socketMessage = JSON.parse(res.data);

                    monitor.callback({
                        Type: socketMessage.Type,
                        Data: JSON.parse(socketMessage.Data)
                    });
                })
            };
            wsObj.onopen = (event) => {
                console.log("onopen", event);
                wbSocket.isConnectState = true;

                Notification.success({
                    title: 'webscoket提示',
                    message: 'wb连接成功'
                })

            }
            wsObj.onclose = (event) => {
                console.log("onclose", event);
                wbSocket.isConnectState = false;
                Notification.error({
                    title: 'webscoket提示',
                    message: 'wb连接关闭'
                })
                RetryConnect();
            }
        }
        else {
            console.log("没有用户id暂不连接");
            RetryConnect();
        }
    }, timeout);



}

//发送消息
export async function SendMessage(type, data) {
    let message = {
        Type: type,
        Data: JSON.stringify(data)
    };
    wsObj.send(JSON.stringify(message));
}
//手动关闭websocket
export async function CloseWebScoket(type, data) {
    if (wsObj) {
        console.log("手动关闭websocket");
        this.wsObj.close();
    }
}


export default {
    AddSocketMessageMonitor,
    RetryConnect,
    SendMessage,
    CloseWebScoket,

    RemoveSocketMessageMonitor
}