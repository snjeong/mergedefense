package com.fruttidino.api.common.util;

import lombok.Data;

public class PlatformLogBuilder {

    @Data
    public class PlatformLog{
        String funcNm; //기능명
        String stepNm; //단계
        String subStepNm; //하위단계
        Object obj;

        public String toString(){
            StringBuffer sb = new StringBuffer();
            sb.append("::")
                    .append(funcNm)
                    .append("::")
                    .append(stepNm)
                    .append(subStepNm == null ? "" : "::")
                    .append(subStepNm == null ? "" : subStepNm)
                    .append("::")
                    .append(obj == null ? null : obj.toString());
            return sb.toString();
        }
    }

    private PlatformLog platformLog;

    public PlatformLogBuilder(){
        platformLog = new PlatformLog();
    }

    public static PlatformLogBuilder create(){
        return new PlatformLogBuilder();
    }

    public PlatformLogBuilder setInfo(String funcNm, String stepNm, Object obj){
        platformLog.setFuncNm(funcNm);
        platformLog.setStepNm(stepNm);
        platformLog.setObj(obj);
        return this;
    }

    public PlatformLogBuilder setFuncNm(String funcNm){
        platformLog.setFuncNm(funcNm);
        return this;
    }

    public PlatformLogBuilder setStepNm(String stepNm){
        platformLog.setStepNm(stepNm);
        return this;
    }

    public PlatformLogBuilder setSubStepNm(String subStepNm){
        platformLog.setSubStepNm(subStepNm);
        return this;
    }

    public PlatformLogBuilder setObj(Object obj){
        platformLog.setObj(obj);
        return this;
    }

    public String toString(){
        return platformLog.toString();
    }
}
