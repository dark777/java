package org.haywired.firefly;

public class ImageComponentsDataBean {
        private int cid;
        private String name;
        private int sid;
        public ImageComponentsDataBean(int a, String b, int c) {
                cid = a;
                name = b;
                sid = c;
        }
        public int getcid() {
                return cid;
        }
        public String getname() {
                return name;
        }
        public int getsid() {
                return sid;
        }
}