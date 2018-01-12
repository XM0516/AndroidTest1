package we.com.test1;

import java.util.Map;

/**
 * Created by Ming on 2018/1/12.
 */

public class GetInfo {
    Map<String,Object> getLogininfo(){

        return null;
    }

    String getFriendQuerySignal(boolean isQuery){ //每次进入FriendActivity界面都默认查询好友列表并显示
        String querySignal = null;
        if (isQuery)
            querySignal="doQuery";
        return querySignal;   //如果来自FriendActivity的信号表示要查询好友名称，则返回“doQuery”信号
    }

}
