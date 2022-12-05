package drug.erp.api;
import drug.erp.handle.IMPushImp;
import drug.erp.intercepter.Permission;
import framework.server.Api;
import framework.server.IceSessionContext;

/**
 * @Author: leeping
 * @Date: 2019/12/30 12:44
 */
public interface IMPushModuleI {

    @Api(detail = "发送消息到指定标识的客户端", imp = IMPushImp.class)
    @Permission(ignore = true)
    public void sendMessageToTarget(IceSessionContext context);

    @Api(detail = "ERP检查指定用户是否在线", imp = IMPushImp.class)
    @Permission(ignore = true)
    public void checkUserOnline(IceSessionContext context);

    @Api(detail = "ERP通过mac/ip查看在线设备,返回连接标识(用户OID),逗号分隔", imp = IMPushImp.class)
    @Permission(ignore = true)
    public void checkDeviceOnline(IceSessionContext context);

    @Api(detail = "发送消息到指定标识的在线客户端", imp = IMPushImp.class)
    @Permission(ignore = true)
    public void sendMessageToOnlineTarget(IceSessionContext context);

    @Api(detail = "发送消息到指定类别的在线客户端", imp = IMPushImp.class)
    @Permission(ignore = true)
    public void sendMessageToOnlineTargetAll(IceSessionContext context);



}
