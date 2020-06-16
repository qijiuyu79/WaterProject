package com.water.project.utils.ble;

import android.content.Intent;
import android.os.Handler;

import com.water.project.service.BleService;
import com.water.project.utils.BleUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lyn on 2017/4/28.
 */

public class SendBleDataManager {

    private static SendBleDataManager sendBleData = new SendBleDataManager();
    private ExecutorService fixedThreadPool_ble = Executors.newSingleThreadExecutor();
    private BleService mService;

    private SendBleDataManager() {
    }


    public static SendBleDataManager getInstance() {
        return sendBleData;
    }


    public void init(BleService mService) {
        this.mService = mService;
    }

    /**
     *
     * @param data  发送的蓝牙命令
     */
    private Handler handler=new Handler();
    public void sendData(final String data) {
        fixedThreadPool_ble.execute(new Runnable() {
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //将字符串进行200字节的截取
                        final List<String> sendList= BleUtils.getSendData(data,200);
                        //下发蓝牙命令
                        boolean b = mService.writeRXCharacteristic(sendList);
                        if (!b) {
                            mService.stopTimeOut();
                            Intent intent = new Intent(mService.ACTION_SEND_DATA_FAIL);
                            mService.sendBroadcast(intent);
                        }
                    }
                },100);
            }
        });
    }



}
