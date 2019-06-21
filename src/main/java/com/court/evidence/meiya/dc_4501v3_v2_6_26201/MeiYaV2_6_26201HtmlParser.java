package com.court.evidence.meiya.dc_4501v3_v2_6_26201;

import com.court.evidence.enums.ModelType;
import com.court.evidence.es.model.ReportTreeNode;
import com.court.evidence.parser.ParseNode;
import com.court.evidence.parser.Parser;
import com.court.evidence.parser.ZTreeNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class MeiYaV2_6_26201HtmlParser {
//    private ObjectMapper objectMapper = new ObjectMapper();
    private Pattern V_ZNODE_PATTERN = Pattern.compile("(var zNodes.*\\])", Pattern.DOTALL);

    private MeiYaV2_6_26201ParserSupports parserSupports = new MeiYaV2_6_26201ParserSupports();

    public void parse(String dic) throws IOException {

        // js变量解析树节点
        List<ZTreeNode> zTreeNodeList = Lists.newArrayList();
        File input = new File(dic + "Catalog.html");
        Document catalogDoc = Jsoup.parse(input, "UTF-8");
        Elements scriptElements = catalogDoc.head().getElementsByTag("script");
        for(Element scriptElement : scriptElements){
            Matcher matcher = V_ZNODE_PATTERN.matcher(scriptElement.data());
            if(matcher.find()){
                String varNode = matcher.group();
                String nodeValue = varNode.substring(varNode.indexOf("=") + 1).trim().replace("'", "").replace("\r\n", "").replace("\t", "");
                String[] nodeLines = nodeValue.split("}, *\\{");
                boolean firstNode = true;
                for(String line : nodeLines){
                    if(line.startsWith("[")){
                        line = line.substring(1);
                    }
                    if(line.startsWith("{")){
                        line = line.substring(1);
                    }
                    if(line.endsWith("]")){
                        line = line.substring(0, line.lastIndexOf("]"));
                    }
                    if(line.endsWith("}")){
                        line = line.substring(0, line.lastIndexOf("}"));
                    }
                    String[] pairs = line.split(",");
                    ZTreeNode zTreeNode = new ZTreeNode();
                    for(String pairStr : pairs){
                        String[] pair = pairStr.trim().split(":");
                        if("id".equals(pair[0])){
                            zTreeNode.setId(Integer.valueOf(pair[1]));
                        } else if("pId".equals(pair[0])){
                            zTreeNode.setPId(Integer.valueOf(pair[1]));
                        } else if("name".equals(pair[0])){
                            zTreeNode.setName(pair[1]);
                        } else if("target".equals(pair[0])){
                            zTreeNode.setTarget(pair[1]);
                        } else if("icon".equals(pair[0])){
                            zTreeNode.setIcon(pair[1]);
                        } else if("url".equals(pair[0])){
                            zTreeNode.setUrl(pair[1]);
                        } else if("open".equals(pair[0])){
                            zTreeNode.setOpen(Boolean.valueOf(pair[1]) ? 1 : 0);
                        }
                    }
                    if(firstNode){
                        zTreeNode.setPId(null);
                        firstNode = false;
                    }
                    zTreeNodeList.add(zTreeNode);
                }
                break;
            } else {
                continue;
            }
        }

        if(CollectionUtils.isEmpty(zTreeNodeList)){
            throw new RuntimeException("not found ztree node");
        }

        // 分辨叶子节点
        List<Integer> idList = zTreeNodeList.stream().map(node -> node.getId()).collect(Collectors.toList());
        List<Integer> pIdList = zTreeNodeList.stream().filter(node -> node.getPId() != null).map(node -> node.getPId()).collect(Collectors.toList());
        idList.removeAll(pIdList);
        zTreeNodeList.stream().forEach(node -> {
            if(idList.contains(node.getId())) {
                node.setLeaf(true);
            } else {
                node.setLeaf(false);
            }});

        // 分辨节点层次
        Map<Integer, ZTreeNode> idZTreeNodeMap = zTreeNodeList.stream().collect(Collectors.toMap(ZTreeNode::getId, Function.identity()));
        for(ZTreeNode zTreeNode : zTreeNodeList){
            ZTreeNode parentNode = zTreeNode.getPId() != null ? idZTreeNodeMap.get(zTreeNode.getPId()) : null;
            if(parentNode != null){
                zTreeNode.setLevel(parentNode.getLevel() + 1);
            } else {
                zTreeNode.setLevel(0);
            }
        }

        // 生成ReportTreeNode
        parserSupports.getParseContext().fromZTreeNodeList(zTreeNodeList);

        List<ReportTreeNode> treeNodeList = parserSupports.getParseContext().treeNodeList();

        confirmModelTypeAndParser(treeNodeList);

        for(ReportTreeNode node : treeNodeList){
            if(node.getLeafFlag() == 0 || node.getCategory().contains("分区1[hda0]")){
                continue;
            }
            int i = 0;
            StringBuilder sb = new StringBuilder();
            while (i < node.getLevel()){
                sb.append("\t");
                i++;
            }
            String category = node.getCategory();
            if(node.getLevel() > 3){
                category = category.substring(category.indexOf(" ___ ") + 5);
                category = category.substring(category.indexOf(" ___ ") + 5);
                category = category.substring(category.indexOf(" ___ ") + 5);
            }


            System.out.println(sb.toString() + "[level=" + node.getLevel() + "," + category + "]");
//            ParseNode parseNode = new ParseNode();
//            parseNode.setNodeId(node.getNodeId());
//            parseNode.setName(node.getCategory());
//            parseNode.setLevel(node.getLevel());
//            parseNodeTreeMap.put(parseNode.getNodeId(), parseNode);
//            if(node.getParentNodeId() != null){
//                parseNodeTreeMap.get(node.getParentNodeId()).addChild(parseNode);
//            } else {
//                parseNodeList.add(parseNode);
//            }
        }

//        ParseNode.print(null, parseNodeList);




        // 解析内容
        File dicFile = new File(dic);
        String[] fileNameArray = dicFile.list();
        if(fileNameArray == null || fileNameArray.length == 0){
            return;
        }
        List<String> fileNameList = Arrays.stream(fileNameArray).filter(name -> name.contains("Contents")).sorted((o1, o2) -> {
                    String o1s = o1.substring(o1.lastIndexOf("Contents") + 8, o1.lastIndexOf("."));
                    String o2s = o2.substring(o2.lastIndexOf("Contents") + 8, o2.lastIndexOf("."));
                    return Integer.valueOf(o1s).compareTo(Integer.valueOf(o2s));
                }
        ).collect(Collectors.toList());

//        System.out.println(fileNameList);


//        for (String fileName : fileNameList) {
//            System.out.println(fileName);
//            String path = dic + fileName;
//            File input = new File(path);
//            Document allDoc = Jsoup.parse(input, "UTF-8");
//            Element body = allDoc.body();
//            if (parserSupports.getParseContext().lastNode() == null) {
//                List<? extends BaseEvidenceModel> searchModelList = parserSupports.reportSummaryParser().parse(body);
//                parserSupports.getParseContext().addNode(CategoryLevel.ROOT, PingHangTitle.RootLevel.REPORT_SUMMARY, true, ModelType.REPORT_SUMMARY, null);
//                fillCommonModelInfo(searchModelList);
//                ModelCollector.receive(searchModelList);
//            } else if(parserSupports.getParseContext().lastNode().getCategory().startsWith(PingHangTitle.RootLevel.SHORT_MESSAGE)){
//                List<? extends BaseEvidenceModel> searchModelList = parserSupports.chatMessageContinueParser().parse(body);
//                fillCommonModelInfo(searchModelList);
//                ModelCollector.receive(searchModelList);
//            } else if(parserSupports.getParseContext().lastNode().getCategory().startsWith(PingHangTitle.RootLevel.CALL_RECORD)){
//                List<? extends BaseEvidenceModel> searchModelList = parserSupports.chatMessageContinueParser().parse(body);
//                fillCommonModelInfo(searchModelList);
//                ModelCollector.receive(searchModelList);
//            }
//
//            Elements aElements = body.getElementsByAttributeValueMatching("class", "^title$|^subtitle$|^subtitle2$|^subtitle3$|^subtitle4$|^subtitle5$");
//            for (Element aElement : aElements) {
//                if (!"a".equals(aElement.tagName())) {
//                    continue;
//                }
//                String category = aElement.text();
//                String title = aElement.attr("class");
//                CategoryLevel categoryLevel = PingHangTitleLevel.getLevel(title);
//                if (categoryLevel == null) {
//                    throw new RuntimeException("not recognized title");
//                }
//                Parser parser = confirmTreeNodeAndParser(categoryLevel, category);
//                if(parser != null){
//                    List<? extends BaseEvidenceModel> searchModelList = parser.parse(aElement);
//                    fillCommonModelInfo(searchModelList);
//                    ModelCollector.receive(searchModelList);
//                }
//            }
//        }
//
//        ModelCollector.receive(parserSupports.getParseContext().getReportSummary());
//        ModelCollector.receiveTree(parserSupports.getParseContext().treeNodeList());
//        parserSupports.getParseContext().printTree();
    }

    private void confirmModelTypeAndParser(List<ReportTreeNode> treeNodeList) {
        for(ReportTreeNode treeNode : treeNodeList){
            ModelType modelType = null;
            Parser parser = null;
            Map<Integer, String> categoryPathMap = parserSupports.getParseContext().getCategoryPath(treeNode);
            if(0 == treeNode.getLevel()){
                //
            } else if(1 == treeNode.getLevel()) {
                if (treeNode.getNodeName().startsWith("案件信息")) {
                    modelType = ModelType.REPORT_SUMMARY;
                    parser = parserSupports.reportSummaryParser();
                }
            } else if(2 == treeNode.getLevel()){

            } else if(3 == treeNode.getLevel()){

            } else if(4 == treeNode.getLevel()){
                if(categoryPathMap.get(3).startsWith("文件信息")){
                    if(treeNode.getNodeName().startsWith("基本信息")){
                        modelType = ModelType.MATERIAL_EVIDENCE;
                        parser = parserSupports.materialEvidenceTitleParser();
                    } else if(treeNode.getNodeName().startsWith("日历")){
                        modelType = ModelType.CALENDAR;
                        parser = parserSupports.calendarTitleParser();
                    } else if(treeNode.getNodeName().startsWith("备忘录")){
                        modelType = ModelType.MEMO;
                        parser = parserSupports.memoTitleParser();
                    } else if(treeNode.getNodeName().startsWith("备忘录")){
                        modelType = ModelType.MEMO;
                        parser = parserSupports.memoTitleParser();
                    }
                }
            } else if(5 == treeNode.getLevel()){
                if(categoryPathMap.get(3).startsWith("文件信息")){
                    if((categoryPathMap.get(4).startsWith("通讯录") || categoryPathMap.get(4).startsWith("已删除通讯录")) && treeNode.getNodeName().startsWith("手机")){
                        modelType = ModelType.ADDRESS_BOOK;
                        parser = parserSupports.addressBookTitleParser();
                    } else if(categoryPathMap.get(4).startsWith("媒体文件")){
                        if(treeNode.getNodeName().startsWith("图片")){
                            modelType = ModelType.MEDIA_FILE_PIC;
                            parser = parserSupports.mediaFilePicTitleParser();
                        } else {
                            modelType = ModelType.MEDIA_FILE;
                            parser = parserSupports.mediaFileTitleParser();
                        }
                    } else if(categoryPathMap.get(4).startsWith("系统日志")){
                        if(treeNode.getNodeName().startsWith("使用过的号码")){
                            modelType = ModelType.USED_PHONE_NUMBER;
                            parser = parserSupports.usedPhoneNumberTitleParser();
                        } else if(treeNode.getNodeName().startsWith("应用程序使用记录")){
                            modelType = ModelType.USAGE_RECORD;
                            parser = parserSupports.usageRecordTitleParser();
                        } else if(treeNode.getNodeName().startsWith("开关机时间")){
                            modelType = ModelType.POWER_ON_OFF_RECORD;
                            parser = parserSupports.powerOnOffTitleParser();
                        } else if(treeNode.getNodeName().startsWith("闹钟")){
                            modelType = ModelType.ALARM_CLOCK;
                            parser = parserSupports.alarmClockTitleParser();
                        }
                    } else if(categoryPathMap.get(4).startsWith("通话记录") || categoryPathMap.get(4).startsWith("已删除通话记录")){
                        modelType = ModelType.CALL_RECORD;
                        parser = parserSupports.callRecordTitleParser();
                    } else if(categoryPathMap.get(4).startsWith("短信息") || categoryPathMap.get(4).startsWith("已删除短信息")){
                        modelType = ModelType.CHAT_MESSAGE;
                        parser = parserSupports.chatMessageTitleParser();
                    } else if(categoryPathMap.get(4).startsWith("位置信息")){
                        if(treeNode.getNodeName().startsWith("应用程序地理位置")){
                            modelType = ModelType.GEOGRAPHIC_LOCATION;
                            parser = parserSupports.geographicLocationTitleParser();
                        }
                    }
                } else if(categoryPathMap.get(3).startsWith("即时通讯")){
                    if(categoryPathMap.get(4).startsWith("Zalo")){
                        if(treeNode.getNodeName().startsWith("通讯录")){
                            modelType = ModelType.ADDRESS_BOOK;
                            parser = parserSupports.zaloAddressBookTitleParser();
                        }
                    }
                }
            } else if(6 == treeNode.getLevel()){
                if(categoryPathMap.get(3).startsWith("即时通讯")){
                    if(categoryPathMap.get(4).startsWith("腾讯QQ")){
                        if(treeNode.getNodeName().startsWith("帐号信息")){
                            modelType = ModelType.SOCIAL_ACCOUNT;
                            parser = parserSupports.socialAccountTitleParser();
                        }
                    }
                } else if(categoryPathMap.get(3).startsWith("浏览器")){

                }
            }

            parserSupports.getParseContext().confirmModelTypeAndParser(treeNode.getNodeId(), modelType, parser);
        }
//        if(0 == zTreeNode.getLevel()){
//
//        } else if (1 == zTreeNode.getLevel()) {
//            if (zTreeNode.getName().startsWith(MeiYa2_6_26201Title.FirstLevel.REPORT_SUMMARY)) {
//                modelType = ModelType.REPORT_SUMMARY;
//                parser =  parserSupports.reportSummaryParser();
//            }
//        } else {
//            ReportTreeNode lastTreeNode = parserSupports.getParseContext().lastNode();
//            if (lastTreeNode == null) {
//                throw new RuntimeException("parse category error, must have the parent node");
//            }
//            if (CategoryLevel.FIRST == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.MEDIA_KIND) && category.startsWith(PingHangTitle.FirstLevel.DEFAULT_ALBUM)) {
//                    leafFlag = true;
//                    modelType = ModelType.MEDIA_FILE;
//                    parser =  parserSupports.fileMediaTitleParser();
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SMART_MINING)) {
//                    leafFlag = true;
//                    if (category.startsWith(PingHangTitle.FirstLevel.BANK_ACCOUNT)
//                            || category.startsWith(PingHangTitle.FirstLevel.ID_CARD)
//                            || category.startsWith(PingHangTitle.FirstLevel.PHONE_NUMBER)
//                            || category.startsWith(PingHangTitle.FirstLevel.EMAIL_ADDRESS)) {
//                        modelType = ModelType.SMART_MINING_COMMON;
//                        parser =  parserSupports.smartMiningCommonTitleParser();
//                    } else if (category.startsWith(PingHangTitle.FirstLevel.FINANCE)) {
//                        modelType = ModelType.SMART_MINING_FINANCE;
//                        parser =  parserSupports.smartMiningFinanceTitleParser();
//                    } else if (category.startsWith(PingHangTitle.FirstLevel.AUTH_CODE)) {
//                        modelType = ModelType.SMART_MINING_AUTH_CODE;
//                        parser =  parserSupports.smartMiningAuthCodeTitleParser();
//                    }
//                }
//            } else if (CategoryLevel.SECOND == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SHORT_MESSAGE)
//                        || lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.CALL_RECORD)) {
//                    leafFlag = true;
//                    if (category.startsWith(PingHangTitle.SecondLevel.LOCAL)) {
//                        modelType = ModelType.LOCAL_CALL_MESSAGE_STATS;
//                        parser =  parserSupports.localCallOrMessageStatsTitleParser();
//                    } else {
//                        modelType = ModelType.CHAT_MESSAGE;
//                        parser =  parserSupports.chatMessageTitleParser();
//                    }
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.MEDIA_FILE)) {
//                    leafFlag = true;
//                    modelType = ModelType.MEDIA_FILE;
//                    parser =  parserSupports.fileMediaTitleParser();
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.MEDIA_KIND)) {
//                    leafFlag = true;
//                    modelType = ModelType.MEDIA_FILE;
//                    parser =  parserSupports.fileMediaTitleParser();
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.APP_ANALYZE)) {
//                    leafFlag = true;
//                    modelType = ModelType.MEDIA_FILE;
//                    parser =  parserSupports.fileMediaTitleParser();
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SMART_MINING)) {
//                    leafFlag = true;
//                    modelType = ModelType.SMART_MINING_COMMON;
//                    parser =  parserSupports.smartMiningCommonTitleParser();
//                }
//            } else if (CategoryLevel.THIRD == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SOCIAL_CHAT)) {
//                    if (category.startsWith(PingHangTitle.ThirdLevel.ACCOUNT_INFO)) {
//                        leafFlag = true;
//                        modelType = ModelType.SOCIAL_ACCOUNT;
//                        parser =  parserSupports.socialAccountTitleParser();
//                    } else if (category.startsWith(PingHangTitle.ThirdLevel.GROUP_INFO)) {
//                        leafFlag = true;
//                        modelType = ModelType.SOCIAL_GROUP;
//                        parser =  parserSupports.socialGroupTitleParser();
//                    } else if (category.startsWith(PingHangTitle.ThirdLevel.BINDING_INFO)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION;
//                        parser =  parserSupports.operationTitleParser();
//                    }
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.MAILBOX)) {
//                    if (category.startsWith(PingHangTitle.ThirdLevel.CONTACT)) {
//                        leafFlag = true;
//                        modelType = ModelType.EMAIL_CONTACT;
//                        parser =  parserSupports.emailAccountTitleParser();
//                    } else if (category.startsWith(PingHangTitle.ThirdLevel.EMAIL)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  parserSupports.operationWithOppositeTitleParser();
//                    }
//                }
//            } else if (CategoryLevel.FOURTH == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SOCIAL_CHAT)) {
//                    if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.FRIEND_STATISTIC)) {
//                        leafFlag = true;
//                        modelType = ModelType.SOCIAL_FRIEND;
//                        parser =  parserSupports.socialFriendTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.GROUP_CONTACT)) {
//                        leafFlag = true;
//                        modelType = ModelType.SOCIAL_GROUP_MEMBER;
//                        parser =  parserSupports.socialGroupMemberTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.GROUP_CHAT)) {
//                        leafFlag = true;
//                        modelType = ModelType.CHAT_MESSAGE;
//                        parser =  parserSupports.chatMessageTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.DELETED_HISTORY)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  parserSupports.operationWithOppositeTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.FRIEND_APPLY)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  parserSupports.operationWithOppositeTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.MINI_PROGRAM)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  parserSupports.operationWithOppositeTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.PAY_INFO)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  parserSupports.operationWithOppositeTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.FRIEND_CIRCLE)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  parserSupports.operationWithOppositeTitleParser();
//                    }
//                }
//            } else if (CategoryLevel.FIFTH == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SOCIAL_CHAT)) {
//                    leafFlag = true;
//                    modelType = ModelType.CHAT_MESSAGE;
//                    parser =  parserSupports.chatMessageTitleParser();
//                }
//            }
//        }
    }

//    private void fillCommonModelInfo(List<? extends BaseEvidenceModel> searchModelList){
//        ReportTreeNode treeNode = parserSupports.getParseContext().lastNode();
//        for(BaseEvidenceModel model : searchModelList){
//            model.setNodeId(treeNode.getNodeId());
//            model.setModelType(treeNode.getModelType());
//            model.setCategory(treeNode.getCategory());
//            model.setCaseId(parserSupports.getParseContext().getReportSummary().getCaseId());
//        }
//    }
}
