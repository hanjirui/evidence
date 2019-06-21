//package com.court.evidence.pinghang;
//
//import com.court.evidence.enums.ModelType;
//import com.court.evidence.es.model.BaseEvidenceModel;
//import com.court.evidence.es.model.ReportTreeNode;
//import com.court.evidence.manager.ModelCollector;
//import com.court.evidence.parser.Parser;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class PingHangHtmlParser {
//    private PingHangParserSupports pingHangParserSupports = new PingHangParserSupports();
//    public void parse(String dic) throws IOException {
//        File dicFile = new File(dic);
//        String[] fileNameArray = dicFile.list();
//        if(fileNameArray == null || fileNameArray.length == 0){
//            return;
//        }
//        List<String> fileNameList = Arrays.stream(fileNameArray).filter(name -> name.contains("Page")).sorted((o1, o2) -> {
//                    String o1s = o1.substring(o1.lastIndexOf("_") + 1, o1.lastIndexOf("."));
//                    String o2s = o2.substring(o2.lastIndexOf("_") + 1, o2.lastIndexOf("."));
//                    return Integer.valueOf(o1s).compareTo(Integer.valueOf(o2s));
//                }
//        ).collect(Collectors.toList());
//
//        for (String fileName : fileNameList) {
//            System.out.println(fileName);
//            String path = dic + fileName;
//            File input = new File(path);
//            Document allDoc = Jsoup.parse(input, "UTF-8");
//            Element body = allDoc.body();
//            if (pingHangParserSupports.getParseContext().lastNode() == null) {
//                List<? extends BaseEvidenceModel> searchModelList = pingHangParserSupports.reportSummaryParser().parse(body);
//                pingHangParserSupports.getParseContext().addNode(0, PingHangTitle.RootLevel.REPORT_SUMMARY, true, ModelType.REPORT_SUMMARY, null);
//                fillCommonModelInfo(searchModelList);
//                ModelCollector.receive(searchModelList);
//            } else if(pingHangParserSupports.getParseContext().lastNode().getCategory().startsWith(PingHangTitle.RootLevel.SHORT_MESSAGE)){
//                List<? extends BaseEvidenceModel> searchModelList = pingHangParserSupports.chatMessageContinueParser().parse(body);
//                fillCommonModelInfo(searchModelList);
//                ModelCollector.receive(searchModelList);
//            } else if(pingHangParserSupports.getParseContext().lastNode().getCategory().startsWith(PingHangTitle.RootLevel.CALL_RECORD)){
//                List<? extends BaseEvidenceModel> searchModelList = pingHangParserSupports.chatMessageContinueParser().parse(body);
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
//                Integer categoryLevel = PingHangTitleLevel.getLevel(title);
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
//        ModelCollector.receive(pingHangParserSupports.getParseContext().getReportSummary());
//        ModelCollector.receiveTree(pingHangParserSupports.getParseContext().treeNodeList());
////        pingHangParserSupports.getParseContext().printTree();
//    }
//
//    private Parser confirmTreeNodeAndParser(Integer categoryLevel, String category) {
//        boolean leafFlag = false;
//        ModelType modelType = null;
//        Parser parser = null;
//        if (0 == categoryLevel) {
//            if (category.startsWith(PingHangTitle.RootLevel.MATERIAL_EVIDENCE)) {
//                leafFlag = true;
//                modelType = ModelType.MATERIAL_EVIDENCE;
//                parser =  pingHangParserSupports.materialEvidenceTitleParser();
//            } else if (category.startsWith(PingHangTitle.RootLevel.OWNER_INFO)) {
//                leafFlag = true;
//                modelType = ModelType.OWNER_INFO;
//                parser =  pingHangParserSupports.ownerTitleParser();
//            } else if (category.startsWith(PingHangTitle.RootLevel.APP_LIST)) {
//                leafFlag = true;
//                modelType = ModelType.APP_LIST;
//                parser =  pingHangParserSupports.appListTitleParser();
//            } else if (category.startsWith(PingHangTitle.RootLevel.APP_STATISTIC)) {
//                leafFlag = true;
//                modelType = ModelType.APP_STATISTIC;
//                parser =  pingHangParserSupports.appStatsTitleParser();
//            } else if (category.startsWith(PingHangTitle.RootLevel.ACCOUNT_INFO)) {
//                leafFlag = true;
//                modelType = ModelType.ACCOUNT;
//                parser =  pingHangParserSupports.accountTitleParser();
//            } else if (category.startsWith(PingHangTitle.RootLevel.ADDRESS_BOOK)) {
//                leafFlag = true;
//                modelType = ModelType.ADDRESS_BOOK;
//                parser =  pingHangParserSupports.addressBookTitleParser();
////            } else if (category.startsWith(PingHangTitle.RootLevel.SHORT_MESSAGE)) {
////            } else if (category.startsWith(PingHangTitle.RootLevel.CALL_RECORD)) {
////            } else if (category.startsWith(PingHangTitle.RootLevel.MEDIA_FILE)) {
////            } else if (category.startsWith(PingHangTitle.RootLevel.MEDIA_KIND)) {
////            } else if (category.startsWith(PingHangTitle.RootLevel.SOCIAL_CHAT)) {
////            } else if (category.startsWith(PingHangTitle.RootLevel.APP_ANALYZE)) {
////            } else if (category.startsWith(PingHangTitle.RootLevel.MAILBOX)) {
////            } else if (category.startsWith(PingHangTitle.RootLevel.SMART_MINING)) {
////            } else if (category.startsWith(PingHangTitle.RootLevel.TAG)) {
////            } else {
////                log.warn("unknown root category:" + category);
//            }
//        } else {
//            ReportTreeNode lastTreeNode = pingHangParserSupports.getParseContext().lastNode();
//            if (lastTreeNode == null) {
//                throw new RuntimeException("parse category error, must have the parent node");
//            }
//            if (1 == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.MEDIA_KIND) && category.startsWith(PingHangTitle.FirstLevel.DEFAULT_ALBUM)) {
//                    leafFlag = true;
//                    modelType = ModelType.MEDIA_FILE;
//                    parser =  pingHangParserSupports.fileMediaTitleParser();
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SMART_MINING)) {
//                    leafFlag = true;
//                    if (category.startsWith(PingHangTitle.FirstLevel.BANK_ACCOUNT)
//                            || category.startsWith(PingHangTitle.FirstLevel.ID_CARD)
//                            || category.startsWith(PingHangTitle.FirstLevel.PHONE_NUMBER)
//                            || category.startsWith(PingHangTitle.FirstLevel.EMAIL_ADDRESS)) {
//                        modelType = ModelType.SMART_MINING_COMMON;
//                        parser =  pingHangParserSupports.smartMiningCommonTitleParser();
//                    } else if (category.startsWith(PingHangTitle.FirstLevel.FINANCE)) {
//                        modelType = ModelType.SMART_MINING_FINANCE;
//                        parser =  pingHangParserSupports.smartMiningFinanceTitleParser();
//                    } else if (category.startsWith(PingHangTitle.FirstLevel.AUTH_CODE)) {
//                        modelType = ModelType.SMART_MINING_AUTH_CODE;
//                        parser =  pingHangParserSupports.smartMiningAuthCodeTitleParser();
//                    }
//                }
//            } else if (2 == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SHORT_MESSAGE)
//                        || lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.CALL_RECORD)) {
//                    leafFlag = true;
//                    if (category.startsWith(PingHangTitle.SecondLevel.LOCAL)) {
//                        modelType = ModelType.LOCAL_CALL_MESSAGE_STATS;
//                        parser =  pingHangParserSupports.localCallOrMessageStatsTitleParser();
//                    } else {
//                        modelType = ModelType.CHAT_MESSAGE;
//                        parser =  pingHangParserSupports.chatMessageTitleParser();
//                    }
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.MEDIA_FILE)) {
//                    leafFlag = true;
//                    modelType = ModelType.MEDIA_FILE;
//                    parser =  pingHangParserSupports.fileMediaTitleParser();
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.MEDIA_KIND)) {
//                    leafFlag = true;
//                    modelType = ModelType.MEDIA_FILE;
//                    parser =  pingHangParserSupports.fileMediaTitleParser();
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.APP_ANALYZE)) {
//                    leafFlag = true;
//                    modelType = ModelType.MEDIA_FILE;
//                    parser =  pingHangParserSupports.fileMediaTitleParser();
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SMART_MINING)) {
//                    leafFlag = true;
//                    modelType = ModelType.SMART_MINING_COMMON;
//                    parser =  pingHangParserSupports.smartMiningCommonTitleParser();
//                }
//            } else if (3 == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SOCIAL_CHAT)) {
//                    if (category.startsWith(PingHangTitle.ThirdLevel.ACCOUNT_INFO)) {
//                        leafFlag = true;
//                        modelType = ModelType.SOCIAL_ACCOUNT;
//                        parser =  pingHangParserSupports.socialAccountTitleParser();
//                    } else if (category.startsWith(PingHangTitle.ThirdLevel.GROUP_INFO)) {
//                        leafFlag = true;
//                        modelType = ModelType.SOCIAL_GROUP;
//                        parser =  pingHangParserSupports.socialGroupTitleParser();
//                    } else if (category.startsWith(PingHangTitle.ThirdLevel.BINDING_INFO)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION;
//                        parser =  pingHangParserSupports.operationTitleParser();
//                    }
//                } else if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.MAILBOX)) {
//                    if (category.startsWith(PingHangTitle.ThirdLevel.CONTACT)) {
//                        leafFlag = true;
//                        modelType = ModelType.EMAIL_CONTACT;
//                        parser =  pingHangParserSupports.emailAccountTitleParser();
//                    } else if (category.startsWith(PingHangTitle.ThirdLevel.EMAIL)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  pingHangParserSupports.operationWithOppositeTitleParser();
//                    }
//                }
//            } else if (4 == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SOCIAL_CHAT)) {
//                    if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.FRIEND_STATISTIC)) {
//                        leafFlag = true;
//                        modelType = ModelType.SOCIAL_FRIEND;
//                        parser =  pingHangParserSupports.socialFriendTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.GROUP_CONTACT)) {
//                        leafFlag = true;
//                        modelType = ModelType.SOCIAL_GROUP_MEMBER;
//                        parser =  pingHangParserSupports.socialGroupMemberTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.GROUP_CHAT)) {
//                        leafFlag = true;
//                        modelType = ModelType.CHAT_MESSAGE;
//                        parser =  pingHangParserSupports.chatMessageTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.DELETED_HISTORY)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  pingHangParserSupports.operationWithOppositeTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.FRIEND_APPLY)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  pingHangParserSupports.operationWithOppositeTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.MINI_PROGRAM)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  pingHangParserSupports.operationWithOppositeTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.PAY_INFO)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  pingHangParserSupports.operationWithOppositeTitleParser();
//                    } else if (lastTreeNode.getSubCategory3().startsWith(PingHangTitle.ThirdLevel.FRIEND_CIRCLE)) {
//                        leafFlag = true;
//                        modelType = ModelType.OPERATION_OPPOSITE;
//                        parser =  pingHangParserSupports.operationWithOppositeTitleParser();
//                    }
//                }
//            } else if (5 == categoryLevel) {
//                if (lastTreeNode.getCategory().startsWith(PingHangTitle.RootLevel.SOCIAL_CHAT)) {
//                    leafFlag = true;
//                    modelType = ModelType.CHAT_MESSAGE;
//                    parser =  pingHangParserSupports.chatMessageTitleParser();
//                }
//            }
//        }
//        pingHangParserSupports.getParseContext().addNode(categoryLevel, category, leafFlag, modelType, parser);
//        return parser;
//    }
//
//    private void fillCommonModelInfo(List<? extends BaseEvidenceModel> searchModelList){
//        ReportTreeNode treeNode = pingHangParserSupports.getParseContext().lastNode();
//        for(BaseEvidenceModel model : searchModelList){
//            model.setNodeId(treeNode.getNodeId());
//            model.setModelType(treeNode.getModelType());
//            model.setCategory(treeNode.getCategory());
//            model.setCaseId(pingHangParserSupports.getParseContext().getReportSummary().getCaseId());
//        }
//    }
//}
