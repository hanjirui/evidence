package com.court.evidence;

import com.court.evidence.pinghang.PingHangHtmlParser;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsoupTest {

    public static void main(String[] args) throws IOException {
        String dic = "/Users/jirui/Downloads/华为_Mate7_20181228205905_报告/Content/";
        new PingHangHtmlParser().parse(dic);
    }
}
