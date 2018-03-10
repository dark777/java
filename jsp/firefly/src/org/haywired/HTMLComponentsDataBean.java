package org.haywired;

public class HTMLComponentsDataBean {
        private int cid;
        private String data;
        private boolean wiki;
        public HTMLComponentsDataBean(int a, String b, int c) {
                cid = a;
                data = b;
                if(c > 0) wiki = true; else wiki = false;
        }
        public int getcid() {
                return cid;
        }
        public String getdata() {
                return data;
        }
        public boolean getwiki() {
                return wiki;
        }
}