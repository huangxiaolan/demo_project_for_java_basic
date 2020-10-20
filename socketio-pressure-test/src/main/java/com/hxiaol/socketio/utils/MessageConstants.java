package com.hxiaol.socketio.utils;

/**
 * 服务端与客户端之间交互的事件
 * 
 */
public interface MessageConstants {

    // 通用事件
    String LOGIN_RES = "login_res";
    String RE_LOGIN_RES = "re_login_res";
    String USER_LOGIN_RES = "user_login_res";
    String HEART_REQ = "heartbeat_req";
    String HEART_RES = "heartbeat_res";
    String LOAD_PROGRESS_REQ="load_progress_req";   // 游戏加载进度
    String LOAD_PROGRESS_RES="load_progress_res";
    String READY_REQ="ready_req";
    String READY_RES="ready_res";


    String READY_GO_RES ="ready_go_res";


    
    String GIVE_UP_REQ = "give_up_req";
    String GIVE_UP_RES = "give_up_res";
    String FORCE_EXIT_REQ = "force_exit_req";
    String FORCE_EXIT_RES = "force_exit_res";
    String GAME_OVER_RES = "game_over_res";

    String UNVARNISHED_REQ = "unvarnished_req";
    String UNVARNISHED_RES = "unvarnished_res";
    
    // 新增事件
    String ACCELERATE_REQ = "accelerate_req";
    String ACCELERATE_RES = "accelerate_res";
    String ADVENTURE_REQ = "adventure_req";
    String ADVENTURE_RES = "adventure_res";
    String ANIMAL_COMPOSE_REQ = "animal_compose_req";
    String ANIMAL_COMPOSE_RES = "animal_compose_res";
    String ANIMAL_EXCHANGE_REQ = "animal_exchange_req";
    String ANIMAL_EXCHANGE_RES = "animal_exchange_res";
    String ANIMAL_GET_ALL_REQ = "animal_get_all_req";
    String ANIMAL_GET_ALL_RES = "animal_get_all_res";
    String ANIMAL_SELL_REQ = "animal_sell_req";
    String ANIMAL_SELL_RES = "animal_sell_res";
    String DES_GET_ALL_REQ = "des_get_all_req";
    String DES_GET_ALL_RES = "des_get_all_res";

    String GIFT_GET_REQ = "gift_get_req";
    String GIFT_GET_RES = "gift_get_res";
    String GIFT_UPGRADE_REQ = "gift_upgrade_req";
    String GIFT_UPGRADE_RES = "gift_upgrade_res";

    String SHOP_BUY_ANIMAL_REQ = "shop_buy_animal_req";
    String SHOP_BUY_ANIMAL_RES = "shop_buy_animal_res";
    String SHOP_GET_ALL_ANIMAL_REQ = "shop_get_all_animal_req";
    String SHOP_GET_ALL_ANIMAL_RES = "shop_get_all_animal_res";
    String SHOP_QUICK_BUY_ANIMAL_REQ = "shop_quick_buy_animal_req";
    String SHOP_QUICK_BUY_ANIMAL_RES = "shop_quick_buy_animal_res";
    String SIGN_GET_ALL_REQ = "sign_get_all_req";
    String SIGN_GET_ALL_RES = "sign_get_all_res";
    String SIGN_GET_AWARD_REQ = "sign_get_award_req";
    String SIGN_GET_AWARD_RES = "sign_get_award_res";
    String SIGN_REQ = "sign_in_req";
    String SIGN_RES = "sign_in_res";
    String TASK_GET_ALL_REQ = "task_get_all_req";
    String TASK_GET_ALL_RES = "task_get_all_res";
    String TASK_GET_AWARD_REQ = "task_get_award_req";
    String TASK_GET_AWARD_RES = "task_get_award_res";
    String TASK_MAIN_GET_REQ = "task_main_get_req";
    String TASK_MAIN_GET_RES = "task_main_get_res";
    String TASK_REFRESH_REQ = "task_refresh_req";
    String TASK_REFRESH_RES = "task_refresh_res";
    String TURNPLATE_GET_TIME_REQ = "turnplate_get_time_req";
    String TURNPLATE_GET_TIME_RES = "turnplate_get_time_res";
    String TURNPLATE_REQ = "turnplate_req";
    String TURNPLATE_RES = "turnplate_res";

    String LEVEL_AWARD_ACHIEVE_REQ = "level_award_achieve_req";
    String LEVEL_AWARD_ACHIEVE_RES = "level_award_achieve_res";

    String OFFLINE_AWARD_REQ = "offline_award_req";
    String OFFLINE_AWARD_RES = "offline_award_res";


    String TEST_SCHEDULE_NAME = "testSchedule" ;

}
