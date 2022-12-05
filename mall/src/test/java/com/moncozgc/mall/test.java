package com.moncozgc.mall;

import com.moncozgc.mall.common.utils.QRCodeUtil;
import com.moncozgc.mall.dto.Base64Dto;

import java.io.File;
import java.io.IOException;

/**
 * Created by MoncozGC on 2022/12/5
 */
public class test {
    public static void main(String[] args) throws IOException {
        File f = new File(new test().getClass().getResource("").getPath());
        System.out.println(f);

        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath();
        System.out.println(courseFile);

        System.out.println(System.getProperty("user.dir") + "\\mall\\src\\main\\data");

        Base64Dto base64Dto = new Base64Dto();
        base64Dto.setBase64str("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIwAAACMCAIAAAAhotZpAAADYElEQVR42u3bvWsUQRjH8YudlWAtEomoSIgXbRTfQAS7FIqlEI6DU2QLsRExkhSxUIkICuJLr40BqyCJWsbGv8JgIRI7G8n6wMKwmCibm3nmZef741g2e3t7u/u53XnZSack0afDKQCJgAQSAYmABBIBiYAEEgEJJAISAQkkAhIBCSQCErFC6iikyfb/tU6T/dzuco3jAgkkkEACSRdJYzuulrv63mDnBySQQAIJJCcHaVP4+6ws+DwukEACCSSQ0kMKVWiDBBJIIIEEksuOTu3GMkgggQQSSHl1sNqso93gpRccJJBAAileJO2BKKGWM1oIJJBAAikNpFBxdYI0OnZVjhckkEACCSTnjdBQBbI2avID9kECCSSQWoDkqqNT+7MxPHgECaR0kGxu0SBFhFT/luyQXN1qPB32UPtjU2nKGmm4kwhSAkibvxQkkOJG0ivq5yYPO6y5aTRgc0cSoeoFUgJIdSeQ4kJ6PVv8ev/g27v7MjULf1+elJesdubUCXnJkmrmaHdCFh46sL9amBeSq87K5gW1+XNq727hWbxxqXdsTGbkrYpnfNfO+pqGykxdXWGuOqDbjCR3ubczV26e73651/teFAapinj8XF83MFcHfSddRCBtD+nx9AW5gIpzRwZnx38szstbC9099c/Wr57i+jWQAiCJ0KvBlEzlkqreMlfS2L5Rs+bc7N0tb3SZIpV+IzzVy9TuBGlj6Y1M5ebW701XMHdu33q08PD0yeOb91Np4Ep0HaxhkaQokumnZ0/Nwo0nM9XM59XVtbWvMvPxw8rLF8+33E+Q1LNjZGR5vn/x4OjQ+wmSeoZ+RpU1kvaA/eYPHRpu37JTlf+qACljpL8+CFJ0SP9/8JEFks0Bl4GiMdQrmcYsSCCVrmhBUkeyv++D5HggfFoVnAR6HEACCSSQWoYUWxVZo8NU40cJEkgggQRSGzpY9RrFGhWZTHvBQQIJJJCiaszG3Ci2KeRtBn2CBBJIIIEUVwerz4JaOyCBBBJIIOWLpF3gazSQQQIJJJBAag+Sz0axTaWgVQP2QQIJJJBahqSxHZ8VBxtgkEACCSSQwiNpD0TxeXI1flgggQQSSCDpIhE/AQkkAhJIBCQCEkgEJAISSAQkkAhIBCSQCEgEJJAISKT8A4OuD8JvBtlYAAAAAElFTkSuQmCC");
        String base64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIwAAACMCAIAAAAhotZpAAADYElEQVR42u3bvWsUQRjH8YudlWAtEomoSIgXbRTfQAS7FIqlEI6DU2QLsRExkhSxUIkICuJLr40BqyCJWsbGv8JgIRI7G8n6wMKwmCibm3nmZef741g2e3t7u/u53XnZSack0afDKQCJgAQSAYmABBIBiYAEEgEJJAISAQkkAhIBCSQCErFC6iikyfb/tU6T/dzuco3jAgkkkEACSRdJYzuulrv63mDnBySQQAIJJCcHaVP4+6ws+DwukEACCSSQ0kMKVWiDBBJIIIEEksuOTu3GMkgggQQSSHl1sNqso93gpRccJJBAAileJO2BKKGWM1oIJJBAAikNpFBxdYI0OnZVjhckkEACCSTnjdBQBbI2avID9kECCSSQWoDkqqNT+7MxPHgECaR0kGxu0SBFhFT/luyQXN1qPB32UPtjU2nKGmm4kwhSAkibvxQkkOJG0ivq5yYPO6y5aTRgc0cSoeoFUgJIdSeQ4kJ6PVv8ev/g27v7MjULf1+elJesdubUCXnJkmrmaHdCFh46sL9amBeSq87K5gW1+XNq727hWbxxqXdsTGbkrYpnfNfO+pqGykxdXWGuOqDbjCR3ubczV26e73651/teFAapinj8XF83MFcHfSddRCBtD+nx9AW5gIpzRwZnx38szstbC9099c/Wr57i+jWQAiCJ0KvBlEzlkqreMlfS2L5Rs+bc7N0tb3SZIpV+IzzVy9TuBGlj6Y1M5ebW701XMHdu33q08PD0yeOb91Np4Ep0HaxhkaQokumnZ0/Nwo0nM9XM59XVtbWvMvPxw8rLF8+33E+Q1LNjZGR5vn/x4OjQ+wmSeoZ+RpU1kvaA/eYPHRpu37JTlf+qACljpL8+CFJ0SP9/8JEFks0Bl4GiMdQrmcYsSCCVrmhBUkeyv++D5HggfFoVnAR6HEACCSSQWoYUWxVZo8NU40cJEkgggQRSGzpY9RrFGhWZTHvBQQIJJJCiaszG3Ci2KeRtBn2CBBJIIIEUVwerz4JaOyCBBBJIIOWLpF3gazSQQQIJJJBAag+Sz0axTaWgVQP2QQIJJJBahqSxHZ8VBxtgkEACCSSQwiNpD0TxeXI1flgggQQSSCDpIhE/AQkkAhJIBCQCEkgEJAISSAQkkAhIBCSQCEgEJJAISKT8A4OuD8JvBtlYAAAAAElFTkSuQmCC";
        String path = System.getProperty("user.dir") + "\\mall\\src\\main\\data";
        QRCodeUtil.GenerateImage(base64Dto, path);
    }
}
