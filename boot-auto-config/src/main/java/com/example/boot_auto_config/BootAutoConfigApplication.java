package com.example.boot_auto_config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootAutoConfigApplication {

    @Bean
    ApplicationRunner run(final ConditionEvaluationReport report) {
        return args -> {
            long totalBeanCnt = report.getConditionAndOutcomesBySource()
                    .entrySet() // Set<Entry<String, ConditionAndOutcomes>>
                    .stream()
                    .filter(cond -> cond.getValue().isFullMatch()) // 조건이 매칭되어 등록된 빈만
                    .filter(cond -> !cond.getKey().contains("Jmx")) // Jmx 관련 제외
                    .map(cond -> {
                        System.out.println(cond.getKey()); // 등록된 빈 클래스 이름
                        cond.getValue().forEach(c -> {
                            System.out.println("\t" + c.getOutcome()); // 조건이 매칭된 이유
                        });
                        System.out.println();
                        return cond;
                    }).count();
            System.out.println("스프링 부트 자동 구성 빈 개수(Jmx 제외): " + totalBeanCnt);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(BootAutoConfigApplication.class, args);
    }

}
