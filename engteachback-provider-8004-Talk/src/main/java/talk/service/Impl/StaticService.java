package talk.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import talk.entity.UserTalk;
import talk.service.UserTalkService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author codeofwhite
 * @date 2024/7/18
 * @Description
 */
@Service
public class StaticService {

    @Autowired
    private UserTalkService userTalkService;

    // 分析用户的平均准确度
    public double calculateAverageAccuracy(String userEmail) {
        List<UserTalk> userTalks = userTalkService.findAll().stream()
                .filter(talk -> talk.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());

        return userTalks.stream()
                .flatMap(talk -> talk.getAccuracy().stream())
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // 分析用户的平均流利度
    public double calculateAverageFluency(String userEmail) {
        List<UserTalk> userTalks = userTalkService.findAll().stream()
                .filter(talk -> talk.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());

        return userTalks.stream()
                .flatMap(talk -> talk.getFluency().stream())
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // 分析用户的平均完整性
    public double calculateAverageIntegrity(String userEmail) {
        List<UserTalk> userTalks = userTalkService.findAll().stream()
                .filter(talk -> talk.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());

        return userTalks.stream()
                .flatMap(talk -> talk.getIntegrity().stream())
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
}
