package util;

import model.StringType;

import java.util.*;

public class StringParser {

    private static List<String> pureHtmlTags = new ArrayList<>(Arrays.asList("a", "abbr", "acronym",
            "address", "area", "article", "aside", "base", "bdi", "bdo", "big", "blockquote", "body",
            "b", "br", "button", "canvas", "caption", "center", "cite", "code", "col", "colgroup", "command",
            "datalist", "dd", "del", "details", "dir", "div", "dfn", "dialog", "dl", "dt", "em", "embed",
            "fieldset", "figcaption", "figure", "font", "footer", "form", "frame", "frameset", "h1", "h2",
            "h3", "h4", "h5", "h6", "head", "header", "hr", "html", "i", "iframe", "img", "input", "ins",
            "isindex", "kbd", "keygen", "label", "legend", "li", "link", "map", "mark", "menu", "meta",
            "meter", "nav", "noframes", "noscript", "object", "ol", "optgroup", "option", "output",
            "p", "param", "pre", "progress", "q", "rp", "rt", "ruby", "s", "samp", "script", "section",
            "select", "source", "span", "strike", "strong", "style", "sub", "summary", "sup", "table",
            "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "tt",
            "u", "ul", "var", "video", "wbr", "xmp"));

    // linux system commands and php command function
    private static List<String> cmds = new ArrayList<>(Arrays.asList("cat", "chattr", "chgrp", "chmod",
            "chown", "cksum", "cmp", "diff", "diffstat", "file", "find", "tgit", "gitview", "tindent",
            "cut", "ln", "less", "locate", "lsattr", "mattrib", "mc", "mdel", "mdir", "mktemp", "more",
            "mmove", "mread", "mren", "mtools", "mtoolstest", "mv", "od", "paste", "patch", "rcp", "rm",
            "slocate", "split", "tee", "tmpwatch", "touch", "umask", "which", "cp", "whereis", "mcopy",
            "tmshowfat", "rhmask", "scp", "awk", "read", "updatedb",

            "col", "colrm", "comm", "csplit", "ed", "egrep", "ex", "fgrep", "fmt", "fold", "grep", "ispell",
            "jed", "joe", "join", "look", "mtype", "pico", "rgrep", "sed", "sort", "spell", "tr", "expr",
            "uniq", "wc", "let",

            "lprm", "lpr", "lpq", "lpd", "bye", "ftp", "uuto", "uupick", "uucp", "uucico", "tftp", "ncftp",
            "ftpshut", "ftpwho", "ftpcount",

            "cd", "df", "dirs", "du", "edquota", "eject", "mcd", "mdeltree", "mdu", "mkdir", "mlabel", "mmd",
            "mrd", "mzip", "pwd", "quota", "mount", "mmount", "rmdir", "rmt", "stat", "tree", "umount", "ls",
            "quotacheck", "quotaoff", "lndir", "repquota", "quotaon",

            "apachectl", "arpwatch", "dip", "getty", "mingetty", "uux", "telnet", "uulog", "uustat", "ppp-off",
            "netconfig", "nc", "httpd", "ifconfig", "minicom", "mesg", "dnsconf", "wall", "netstat", "ping",
            "pppstats", "samba", "setserial", "talk", "traceroute", "tty", "newaliases", "uuname", "netconf",
            "write", "statserial", "efax", "pppsetup", "tcpdump", "ytalk", "cu", "smbd", "testparm", "smbclient",
            "shapecfg",

            "adduser", "chfn", "useradd", "date", "exit", "finger", "fwhios", "sleep", "suspend", "groupdel",
            "groupmod", "halt", "kill", "last", "lastb", "login", "logname", "logout", "ps", "nice", "procinfo",
            "top", "pstree", "reboot", "rlogin", "rsh", "sliplogin", "screen", "shutdown", "rwho", "sudo", "gitps",
            "swatch", "tload", "logrotate", "uname", "chsh", "userconf", "userdel", "usermod", "vlock", "who", "whoami",
            "whois", "newgrp", "renice", "su", "skill", "groupadd", "free",

            "reset", "clear", "alias", "dircolors", "aumix", "bind", "chroot", "clock", "crontab", "declare", "depmod",
            "dmesg", "enable", "eval", "export", "pwunconv", "grpconv", "rpm", "insmod", "kbdconfig", "lilo", "liloconfig",
            "lsmod", "minfo", "set", "modprobe", "ntsysv", "mouseconfig", "passwd", "pwconv", "rdate", "resize", "rmmod",
            "grpunconv", "modinfo", "time", "setup", "sndconfig", "setenv", "setconsole", "timeconfig", "ulimit", "unset",
            "chkconfig","apmd", "hwclock", "mkkickstart", "fbset", "unalias", "SVGATextMode", "gpasswd",

            "ar", "bunzip2", "bzip2", "bzip2recover", "gunzip", "unarj", "compress", "cpio", "dump", "uuencode", "gzexe",
            "gzip", "lha", "restore", "tar", "uudecode", "unzip", "zip", "zipinfo",

            "setleds", "loadkeys", "rdev", "dumpkeys", "MAKEDEV", "poweroff",

            "document.getElementById", "shell_exec", "exec", "passthru", "system", "eval", "assert", "$_POST",
            "$_GET", "java", "gcc", "clang"));

    private static List<String> htmlTags = new ArrayList<>();

    private static final List<String> specialChars = new ArrayList<>(Arrays.asList("\\x", "\\u", "\\d",
            "^", "%", "&", "===", "==", "+"));

    static {
        pureHtmlTags.forEach(tag -> {
            htmlTags.add("<" + tag);
            htmlTags.add("</" + tag + ">");
            htmlTags.add("<" + tag + "/>");
        });
    }

    public static float entropy(String content){
        char[] chars = content.toCharArray();
        int totalNum = content.length();
        Map<Character, Integer> hm = new HashMap();
        for(char c : chars) {
            //containsKey(c),当c不存在于hm中
            if(!hm.containsKey(c))
                hm.put(c,1);
            else
                //否则获得c的值并且加1
                hm.put(c, hm.get(c)+1);
        }
        float e = 0;
        for(Character key: hm.keySet()){
            int count =  hm.get(key);
            float p = (float)count / totalNum;
            e += -1 * p * Math.log(p) / Math.log(2);
        }
        return e;
    }

    public static StringType classify(String content){
        // judge whether this is a html string
        for (String tag: htmlTags)
            if (content.contains(tag))
                return StringType.HtmlString;

        // long encrypted string
        if (StringParser.entropy(content) > 5)
            return StringType.EncryptedString;

        // the entropy algorithm is not able to detect short encrypted string, we could only match special chars in it
        // which in inefficient but could work in our scenario.
        for (String specialChar: specialChars)
            if (content.contains(specialChar))
                return StringType.EncryptedString;

        // judge whether this is a code string
        for (String cmd: cmds)
            if (content.contains(cmd))
                return StringType.CodeString;
        // if still undetect and the content length is large than 30, it is highly a encrypted string
        if (content.length() > 30)
            return StringType.EncryptedString;

        return StringType.NormalString;
    }
}
