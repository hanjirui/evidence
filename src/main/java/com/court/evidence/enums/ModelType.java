package com.court.evidence.enums;

/**
 * 实体类型枚举
 */
public enum ModelType {
    REPORT_SUMMARY("report_summary"),
    MATERIAL_EVIDENCE("material_evidence"),
    MEMO("memo"),
    CALENDAR("calendar"),
    OWNER_INFO("owner_info"),
    APP_LIST("app_list"),
    APP_STATISTIC("app_statistic"),
    ACCOUNT("account"),
    ADDRESS_BOOK("address_book"),
    LOCAL_CALL_MESSAGE_STATS("local_call_message_stats"),
    MEDIA_FILE("media_file"),
    MEDIA_FILE_PIC("media_file_pic"),
    ALARM_CLOCK("alarm_clock"),
    USED_PHONE_NUMBER("used_phone_number"),
    USAGE_RECORD("usage_record"),
    POWER_ON_OFF_RECORD("power_on_off_record"),
    CALL_RECORD("call_record"),
    GEOGRAPHIC_LOCATION("geographic_location"),
    OPERATION("operation"),
    OPERATION_OPPOSITE("operation_opposite"),
    OPERATION_APP("operation_app"),
    SOCIAL_ACCOUNT("social_account"),
    SOCIAL_FRIEND("social_friend"),
    SOCIAL_GROUP("social_group"),
    SOCIAL_GROUP_MEMBER("social_group_member"),
    EMAIL_CONTACT("email_contact"),
    SMART_MINING_COMMON("smart_mining_common"),
    SMART_MINING_FINANCE("smart_mining_finance"),
    SMART_MINING_AUTH_CODE("smart_mining_auth_code"),
    CHAT_MESSAGE("chat_message");

    private String value;
    ModelType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
