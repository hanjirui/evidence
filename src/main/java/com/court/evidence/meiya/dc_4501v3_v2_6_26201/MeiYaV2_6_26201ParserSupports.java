package com.court.evidence.meiya.dc_4501v3_v2_6_26201;

import com.court.evidence.domain.*;
import com.court.evidence.enums.DeleteFlagType;
import com.court.evidence.enums.FlowDirection;
import com.court.evidence.es.model.Report;
import com.court.evidence.parser.ParseContext;
import com.court.evidence.parser.Parser;
import com.court.evidence.util.DateTimeUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MeiYaV2_6_26201ParserSupports {
    private ParseContext parseContext = new ParseContext();

    public ParseContext getParseContext() {
        return parseContext;
    }

    public Parser reportSummaryParser(){
        return bodyElement -> {
            Element tableElement = locateSummaryTable(bodyElement);
            return parseReportSummary(tableElement);
        };
    }

    Parser materialEvidenceTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMaterialEvidenceTable(tableElement);
        };
    }

    Parser memoTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMaterialEvidenceTable(tableElement);
        };
    }

    Parser calendarTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMaterialEvidenceTable(tableElement);
        };
    }

    Parser ownerTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseOwnerTable(tableElement);
        };
    }

    Parser appListTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseAppListTable(tableElement);
        };
    }

    Parser appStatsTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseAppStatsTable(tableElement);
        };
    }

    Parser accountTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseAccountTable(tableElement);
        };
    }

    Parser addressBookTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseAddressBookTable(tableElement);
        };
    }

    Parser mediaFilePicTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseAddressBookTable(tableElement);
        };
    }

    Parser mediaFileTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMediaFileTable(tableElement);
        };
    }

    Parser usageRecordTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMediaFileTable(tableElement);
        };
    }

    Parser powerOnOffTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMediaFileTable(tableElement);
        };
    }

    Parser alarmClockTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMediaFileTable(tableElement);
        };
    }

    Parser callRecordTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMediaFileTable(tableElement);
        };
    }

    Parser usedPhoneNumberTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMediaFileTable(tableElement);
        };
    }

    Parser geographicLocationTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMediaFileTable(tableElement);
        };
    }

    Parser zaloAddressBookTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseMediaFileTable(tableElement);
        };
    }

    Parser operationTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseOperationTable(tableElement);
        };
    }

    Parser operationWithOppositeTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseOperationWithOppositeTable(tableElement);
        };
    }

    Parser smartMiningCommonTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseSmartMiningCommonTable(tableElement);
        };
    }

    Parser smartMiningFinanceTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseSmartMiningFinanceTable(tableElement);
        };
    }

    Parser smartMiningAuthCodeTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseSmartMiningAuthCodeTable(tableElement);
        };
    }

    Parser localCallOrMessageStatsTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseLocalCallOrMessageStatsTable(tableElement);
        };
    }

    Parser chatMessageTitleParser(){
        return titleElement -> {
            Element chatDivElement = locateChatMessageElement(titleElement);
            return parseChatMessage(chatDivElement);
        };
    }

    Parser chatMessageContinueParser(){
        return body -> {
            Element chatDivElement = locateChatMessageContinueElement(body);
            return parseChatMessage(chatDivElement);
        };
    }

    Parser socialAccountTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseSocialAccountTable(tableElement);
        };
    }

    Parser socialGroupTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseSocialGroupTable(tableElement);
        };
    }

    Parser emailAccountTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseEmailAccountTable(tableElement);
        };
    }

    Parser socialFriendTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseSocialFriendTable(tableElement);
        };
    }

    Parser socialGroupMemberTitleParser(){
        return titleElement -> {
            Element tableElement = locateNextTable(titleElement);
            return parseSocialGroupMemberTable(tableElement);
        };
    }
    
    private List<BigProperty> parseReportSummary(Element tableElement) {
        List<List<String>> summaryLineList = tableValues(tableElement);
        Report reportSummary = new Report();
        BigProperty bigProperty = BigProperty.builder().build();
        for (List<String> summaryLine : summaryLineList) {
            String name = summaryLine.get(0).trim().replace(":", "").replace("：", "");
            if(StringUtils.isEmpty(name)){
                continue;
            }
            String value = summaryLine.get(1);
            bigProperty.addPropertyPair(PropertyPair.builder().name(name).value(value).build());
            switch (name){
                case "案件":
                    reportSummary.setCaseName(value);
                    break;
                case "调查员":
                    reportSummary.setInvestigator(value);
                    break;
                case "报告日期":
                    reportSummary.setReportTime(DateTimeUtil.parseTime(value, DateTimeUtil.DATE_TIME_PATTERN));
                    break;
                case "软件名称":
                    reportSummary.setSoftwareName(value);
                    break;
                case "版本":
                    reportSummary.setVersion(value);
                    break;
                case "序列号":
                    reportSummary.setCaseId(value);
                    break;
                default:
                    log.warn("未被解析的摘要：" + name);
            }
        }
        parseContext.setReportSummary(reportSummary);
        return Lists.newArrayList(bigProperty);
    }

    private List<BigProperty> parseMaterialEvidenceTable(Element tableEle) {
        List<List<String>> pairList = materialTableValues(tableEle);
        BigProperty bigProperty = BigProperty.builder().build();
        pairListToBigText(bigProperty, pairList);
        String background = materialBackground(tableEle);
        bigProperty.addPropertyPair(PropertyPair.builder().name("物证照片").value(background).build());
        parseContext.getReportSummary().setEvidenceName(bigProperty.getPropertyPairValue("物证名称"));
        return Lists.newArrayList(bigProperty);
    }

    private List<BigProperty> parseOwnerTable(Element tableElement) {
        List<List<String>> pairList = tableValues(tableElement);
        BigProperty bigText = BigProperty.builder().build();
        pairListToBigText(bigText, pairList);
        return Lists.newArrayList(bigText);
    }

    private List<App> parseAppListTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<App> appList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            App app = App.builder().build();
            app.setOrderNumber(Integer.valueOf(lineList.get(0)));
            app.setAppName(lineList.get(1));
            app.setVersion(lineList.get(2));
            app.setInstallTime(DateTimeUtil.parseTime(lineList.get(3), DateTimeUtil.DATE_TIME_PATTERN));
            app.setUpgradeTime(DateTimeUtil.parseTime(lineList.get(4), DateTimeUtil.DATE_TIME_PATTERN));
            app.setPackageName(lineList.get(5));
            app.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(6)));
            appList.add(app);
        }
        return appList;
    }

    private List<AppStatistic> parseAppStatsTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<AppStatistic> appStatisticList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            AppStatistic appStatistic = AppStatistic.builder().build();
            appStatistic.setOrderNumber(Integer.valueOf(lineList.get(0)));
            appStatistic.setAppName(lineList.get(1));
            appStatistic.setEventCount(lineList.get(2) != null ? Integer.valueOf(lineList.get(2).trim()) : null);
            appStatistic.setContactsCount(lineList.get(3) != null ? Integer.valueOf(lineList.get(3).trim()) : null);
            appStatistic.setFileCount(lineList.get(4) != null ? Integer.valueOf(lineList.get(4).trim()) : null);
            appStatistic.setDeleteCount(lineList.get(5) != null ? Integer.valueOf(lineList.get(5).trim()) : null);
            appStatistic.setTotalCount(lineList.get(6) != null ? Integer.valueOf(lineList.get(6).trim()) : null);
            appStatistic.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(7)));
            appStatisticList.add(appStatistic);
        }
        return appStatisticList;
    }

    private List<Account> parseAccountTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Account> accountList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Account account = Account.builder().build();
            account.setAppName(lineList.get(0));
            account.setAccountId(lineList.get(1));
            account.setAccountName(lineList.get(2));
            account.setNickname(lineList.get(3));
            account.setPassword(lineList.get(4));
            account.setEmail(lineList.get(5));
            account.setPhoneNumber(lineList.get(6));
            account.setSex(lineList.get(7));
            account.setAddress(lineList.get(8));
            account.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(9)));
            accountList.add(account);
        }
        return accountList;
    }

    private List<Account> parseAddressBookTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Account> accountList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Account account = Account.builder().build();
            fillAccountCommonInfo(lineList, account);
            account.setRealName(lineList.get(3));
            account.setPhoneNumber(lineList.get(4));
            account.setEmail(lineList.get(5));
            account.setAddress(lineList.get(6));
            account.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(7)));
            accountList.add(account);
        }
        return accountList;
    }

    private List<MediaFile> parseMediaFileTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<MediaFile> mediaFileList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            MediaFile mediaFile = MediaFile.builder().build();
            mediaFile.setOrderNumber(Integer.valueOf(lineList.get(0)));
            mediaFile.setActTime(DateTimeUtil.parseTime(lineList.get(1), DateTimeUtil.DATE_TIME_PATTERN));
            mediaFile.setFileType(lineList.get(2));
            mediaFile.setContent(lineList.get(3));
            mediaFile.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(4)));
            mediaFileList.add(mediaFile);
        }
        return mediaFileList;
    }

    private List<Operation> parseOperationTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Operation> operationList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Operation operation = Operation.builder().build();
            operation.setOrderNumber(Integer.valueOf(lineList.get(0)));
            operation.setActTime(DateTimeUtil.parseTime(lineList.get(1), DateTimeUtil.DATE_TIME_PATTERN));
            operation.setAction(lineList.get(2));
            operation.setContent(lineList.get(3));
            operation.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(4)));
            operationList.add(operation);
        }
        return operationList;
    }

    private List<Operation> parseSmartMiningCommonTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Operation> operationList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Operation operation = Operation.builder().build();
            operation.setOrderNumber(Integer.valueOf(lineList.get(0)));
            operation.setActTime(DateTimeUtil.parseTime(lineList.get(1), DateTimeUtil.DATE_TIME_PATTERN));
            operation.setAppName(lineList.get(2));
            operation.setAction(lineList.get(3));
            operation.setOpposite(lineList.get(4));
            operation.setContent(lineList.get(5));
            operation.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(6)));
            operationList.add(operation);
        }
        return operationList;
    }

    private List<Operation> parseSmartMiningFinanceTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Operation> operationList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Operation operation = Operation.builder().build();
            operation.setOrderNumber(Integer.valueOf(lineList.get(0)));
            operation.setActTime(DateTimeUtil.parseTime(lineList.get(1), DateTimeUtil.DATE_TIME_PATTERN));
            operation.setAction(lineList.get(3));
            operation.setContent(lineList.get(4));
            operation.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(5)));
            operationList.add(operation);
        }
        return operationList;
    }

    private List<Operation> parseSmartMiningAuthCodeTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Operation> operationList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Operation operation = Operation.builder().build();
            operation.setOrderNumber(Integer.valueOf(lineList.get(0)));
            operation.setActTime(DateTimeUtil.parseTime(lineList.get(1), DateTimeUtil.DATE_TIME_PATTERN));
            operation.setOpposite(lineList.get(2));
            operation.setContent(lineList.get(3));
            operation.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(4)));
            operationList.add(operation);
        }
        return operationList;
    }

    private List<Account> parseLocalCallOrMessageStatsTable(Element tableElement) {
        List<Account> accountList = Lists.newArrayList();
        List<List<String>> multiLineList = tableValues(tableElement);
        for (List<String> lineList : multiLineList) {
            Account account = Account.builder().build();
            fillAccountCommonInfo(lineList, account);
            account.setPhoneNumber(lineList.get(3));
            account.setEmail(lineList.get(4));
            account.setAddress(lineList.get(5));
            account.setCount(Integer.valueOf(lineList.get(6)));
            account.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(7)));
            accountList.add(account);
        }
        return accountList;
    }

    private List<Message> parseChatMessage(Element chatDivElement) {
        if(chatDivElement == null){
            return Lists.newArrayList();
        }
        Elements elements = chatDivElement.getElementsByAttributeValueMatching("class", "^\\s*(main)\\s*$|^\\s*main\\s*self\\s*$");
        List<Message> messageList = Lists.newArrayList();
        for (Element element : elements) {
            Message message = Message.builder().build();
            if (element.attr("class").trim().equals("main")) {
                message.setFlowDirection(FlowDirection.IN.getValue());
            } else {
                message.setFlowDirection(FlowDirection.OUT.getValue());
            }

            Element imgEle = element.getElementsByTag("img").first();
            message.setAvatar(imgEle != null ? imgEle.attr("src") : null);

            Elements liElements = element.getElementsByTag("li");
            if(!liElements.isEmpty()){
                Element displayNameEle = liElements.first().getElementsByTag("span").first();
                message.setDisplayName(displayNameEle != null ? displayNameEle.text() : null);
            }

            Element textEle = element.getElementsByTag("div").first();
            message.setContent(textEle != null ? textEle.text() : null);

            Element deleteImgEle = textEle == null ? null : textEle.getElementsByTag("img").first();
            if(deleteImgEle != null && deleteImgEle.attr("src").contains("delete_transparent.png")){
                message.setDeleteFlag(DeleteFlagType.DELETE.getValue());
            }

            Element timeEle = element.nextElementSibling().getElementsByTag("span").first();
            message.setActTime(DateTimeUtil.parseTime(timeEle.text(), DateTimeUtil.DATE_TIME_PATTERN));
            messageList.add(message);
        }
        return messageList;
    }

    private List<BigProperty> parseSocialAccountTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        BigProperty bigText = BigProperty.builder().build();
        pairListToBigText(bigText, multiLineList);
        return Lists.newArrayList(bigText);
    }

    private List<Account> parseSocialGroupTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Account> accountList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Account account = Account.builder().build();
            account.setOrderNumber(Integer.valueOf(lineList.get(0)));
            account.setAccountId(lineList.get(1));
            account.setNickname(lineList.get(2));
            account.setCount(Integer.valueOf(lineList.get(3)));
            account.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(4)));
            accountList.add(account);
        }
        return accountList;
    }

    private List<Account> parseEmailAccountTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Account> accountList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Account account = Account.builder().build();
            fillAccountCommonInfo(lineList, account);
            account.setEmail(lineList.get(3));
            account.setPhoneNumber(lineList.get(4));
            account.setSex(lineList.get(5));
            account.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(6)));
            accountList.add(account);
        }
        return accountList;
    }

    private List<Account> parseSocialFriendTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Account> accountList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Account account = Account.builder().build();
            account.setOrderNumber(Integer.valueOf(lineList.get(0)));
            account.setAccountId(lineList.get(1));
            account.setAccountName(lineList.get(2));
            account.setNickname(lineList.get(3));
            account.setRealName(lineList.get(4));
            account.setPhoneNumber(lineList.get(5));
            account.setEmail(lineList.get(6));
            account.setCount(StringUtils.isEmpty(lineList.get(7)) ? null : Integer.valueOf(lineList.get(7).trim()));
            account.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(8)));
            accountList.add(account);
        }
        return accountList;
    }

    private List<GroupMember> parseSocialGroupMemberTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<GroupMember> groupMemberList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            GroupMember groupMember = GroupMember.builder().build();
            groupMember.setOrderNumber(Integer.valueOf(lineList.get(0)));
            groupMember.setGroupAccount(lineList.get(1));
            groupMember.setGroupName(lineList.get(2));
            groupMember.setMemberAccount(lineList.get(3));
            groupMember.setMemberNickname(lineList.get(4));
            groupMember.setDescription(lineList.get(5));
            groupMember.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(6)));
            groupMemberList.add(groupMember);
        }
        return groupMemberList;
    }

    private List<Operation> parseOperationWithOppositeTable(Element tableElement) {
        List<List<String>> multiLineList = tableValues(tableElement);
        List<Operation> operationList = Lists.newArrayList();
        for (List<String> lineList : multiLineList) {
            Operation operation = Operation.builder().build();
            operation.setOrderNumber(Integer.valueOf(lineList.get(0)));
            operation.setActTime(DateTimeUtil.parseTime(lineList.get(1), DateTimeUtil.DATE_TIME_PATTERN));
            operation.setAction(lineList.get(2));
            operation.setOpposite(lineList.get(3));
            operation.setContent(lineList.get(4));
            operation.setDeleteFlag(DeleteFlagType.strToValue(lineList.get(5)));
            operationList.add(operation);
        }
        return operationList;
    }

//    private void fillFileOperationCommonThreeInfo(List<String> lineList, MediaFile mediaFile) {
//        fillFileOperationCommonTwoInfo(lineList, mediaFile);
//        mediaFile.setAction(lineList.get(2));
//    }
//
//    private void fillFileOperationCommonTwoInfo(List<String> lineList, MediaFile mediaFile) {
//        mediaFile.setOrderNumber(Integer.valueOf(lineList.get(0)));
//        mediaFile.setActTime(DateTimeUtil.parseTime(lineList.get(1), DateTimeUtil.DATE_TIME_PATTERN));
//    }

    private void fillAccountCommonInfo(List<String> lineList, Account account) {
        account.setOrderNumber(Integer.valueOf(lineList.get(0)));
        account.setAccountId(lineList.get(1));
        account.setNickname(lineList.get(2));
    }

    private void pairListToBigText(BigProperty bigProperty, List<List<String>> pairList) {
        for (List<String> pair : pairList) {
            bigProperty.addPropertyPair(PropertyPair.builder().name(pair.get(0)).value(pair.get(1)).build());
        }
    }

    private String materialBackground(Element tableElement){
        Elements trElements = tableElement.getElementsByTag("tr");
        for(Element tr : trElements){
            String background = tr.children().first().attr("background");
            if(background != null && background.length() > 0){
                return background;
            }
        }
        return null;
    }

    private Element locateSummaryTable(Element body){
        return body.getElementsByTag("table").first();
    }

    private Element locateNextTable(Element aElement){
        Element next = aElement.nextElementSibling();
        int i = 0;
        while (next != null && i < 10){
            if(next.tagName().equals("table") && "datatable".equals(next.attr("class"))){
                return next;
            }
            if(next.tagName().equals("a")){
                return null;
            }
            next = next.nextElementSibling();
            i++;
        }
        return null;
    }

    private List<List<String>> materialTableValues(Element tableEle){
        if(tableEle == null){
            return Lists.newArrayList();
        }
        Elements trElements = tableEle.getElementsByTag("tr");
        List<List<String>> tableValueList = new ArrayList<>();
        boolean first = true;
        for(Element tr : trElements){
            if(first){
                first = false;
                continue;
            }
            List<String> lineList = new ArrayList<>();
            Elements tdElements = tr.getElementsByTag("td");
            boolean jumpedFirst = tdElements.size() == 3 ? true : false;
            for(Element tdElement : tdElements){
                if(jumpedFirst){
                    jumpedFirst = false;
                    continue;
                }
                lineList.add(tdElement.text());
            }
            lineList.add(DeleteFlagType.UN_DELETE.getStrValue());
            tableValueList.add(lineList);
        }
        return tableValueList;
    }

    private List<List<String>> tableValues(Element tableEle){
        if(tableEle == null){
            return Lists.newArrayList();
        }
        Elements trElements = tableEle.getElementsByTag("tr");
        List<List<String>> tableValueList = new ArrayList<>();
        boolean first = true;
        for(Element tr : trElements){
            if(first){
                first = false;
                continue;
            }
            List<String> lineList = new ArrayList<>();
            Elements tdElements = tr.getElementsByTag("td");
            for(Element tdElement : tdElements){
                lineList.add(tdElement.text());
            }
            lineList.add(DeleteFlagType.UN_DELETE.getStrValue());
            Element deleteImgEle = tr.getElementsByTag("td").first().getElementsByTag("img").first();
            if(deleteImgEle != null){
                if("del_tag".equals(deleteImgEle.attr("class")) && !deleteImgEle.attr("src").contains("undelete.png") && deleteImgEle.attr("src").contains("delete.png")){
                    lineList.remove(lineList.size() - 1);
                    lineList.add(DeleteFlagType.DELETE.getStrValue());
                }
            }
            tableValueList.add(lineList);
        }
        return tableValueList;
    }

    private Element locateChatMessageElement(Element titleElement){
        Element next = titleElement.nextElementSibling();
        if(next.tagName().equals("div") && "chat".equals(next.attr("id"))){
            return next;
        }
        return null;
    }

    private Element locateChatMessageContinueElement(Element body){
        return body.getElementsByAttributeValue("id", "chat_WholePage").first();
    }
}
