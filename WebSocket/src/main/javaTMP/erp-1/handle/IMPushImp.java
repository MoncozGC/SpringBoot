package drug.erp.handle;

import bottle.tuples.Tuple2;
import drug.erp.api.IMPushModuleI;
import framework.server.IceSessionContext;
import framework.server.ServerIceBoxImp;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: leeping
 * @Date: 2019/12/30 16:03
 */
public class IMPushImp implements IMPushModuleI {

    private final static ServerIceBoxImp server = ServerIceBoxImp.get();



    public static void sendMessageToIdentity(String identity,String message){
        server.getService().sendMessageToClient(identity,message);
    }

    /** 查看指定标识是否在线 */
    private static boolean checkOnline(String specIdentity) {
        //System.out.println("查询指定标识是否在线,"+Thread.currentThread()+" > " + specIdentity);
        if (specIdentity!=null){
            for(String identity : server.getService().currentOnlineClientIdentity()){
                if (specIdentity.equals(identity)){
                    return true;
                }
            }
        }
        return false;
    }

    /** 获取ERP在线设备 */
    private static List<String> getERPOnlineDev(String mac,String ip) {
//        System.out.println("获取ERP在线终端,"+Thread.currentThread()+" > "+ mac+ " "+ ip);
        List<String> resultList = new ArrayList<>();
        for(Tuple2<String,String> identity : server.getService().currentOnlineClientIdentityAll()){
            String[] arrays = identity.getValue0().split("@");
            if (arrays.length==2){
                if (mac.equals(arrays[0]) && ip.equals(arrays[1])){
                    resultList.add(identity.getValue1());
                }
            }
        }
        return resultList;
    }

    private List<String> getSpecTypeOnlineDev(String clientType) {
        List<String> resultList = new ArrayList<>();
        if (clientType!=null && clientType.length()>0){
            for(Tuple2<String,String> identity : server.getService().currentOnlineClientIdentityAll()){
                if (identity.getValue0().equals(clientType)){
                    resultList.add(identity.getValue1());
                }
            }
        }

        return resultList;
    }


    @Override
    public void sendMessageToTarget(IceSessionContext context) {
        String[] arrays = context.getArrayParam();
        if (arrays.length != 2) throw new IllegalArgumentException();
        sendMessageToIdentity(arrays[0],arrays[1]);
        context.SUCCESS("消息已接收");
    }

    @Override
    public void checkUserOnline(IceSessionContext context) {
        String[] arrays = context.getArrayParam();
        if (arrays.length != 1)  throw new IllegalArgumentException();
        boolean isOnline = checkOnline(arrays[0]);
        context.getResult().setText(isOnline);
    }

    @Override
    public void checkDeviceOnline(IceSessionContext context) {
        String[] arrays = context.getArrayParam();
        if (arrays.length != 2) throw new IllegalArgumentException();
        String mac = arrays[0];
        String ip = arrays[1];
        StringBuilder result = new StringBuilder();

        if(mac.contains("-") && ip.contains(".")){
            List<String> list = getERPOnlineDev(mac,ip);
            if (list.size()>0){
                for (String str : list){
                    result.append(str).append(",");
                }
                result.deleteCharAt(result.length()-1);
            }
        }

        context.getResult().setText(result.toString());
    }

    @Override
    public void sendMessageToOnlineTarget(IceSessionContext context) {
        String[] arrays = context.getArrayParam();
        if (arrays.length!=2) throw new IllegalArgumentException();
        sendMessageToIdentity(arrays[0],"ON_CACHE:"+arrays[1]);
        context.SUCCESS("消息已接收");
    }

    @Override
    public void sendMessageToOnlineTargetAll(IceSessionContext context) {
        String[] arrays = context.getArrayParam();
        if (arrays.length != 2) throw new IllegalArgumentException();
            String clientType = arrays[0];
            String message = arrays[1];
            List<String> list = getSpecTypeOnlineDev(clientType);
            for (String identity : list){
                sendMessageToIdentity(identity,"ON_CACHE:"+message);
            }
            context.SUCCESS("消息已接收");
    }

}
