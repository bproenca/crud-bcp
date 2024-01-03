package com.github.bproenca.crudbcp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("infra")
public class InfraController {

	private static final Logger log = LoggerFactory.getLogger(InfraController.class);
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/ping")
	public Map<String, Object> ping() {
        log.info("## Ping");
		Map<String, Object> retorno = new HashMap<>();
        retorno.put("endpoint", "ping");
        retorno.put("counter", counter.incrementAndGet());
        retorno.put("when", LocalDateTime.now());
        return retorno;
	}
    
    @GetMapping("/sleep")
	public Map<String, Object> sleep(@RequestParam(value = "time", defaultValue = "2000") Integer time) {
        log.info("## Sleep for {}", time);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(); 
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error("Sleep error", e);
        }
        stopWatch.stop();

		Map<String, Object> retorno = new HashMap<>();
        retorno.put("endpoint", "sleep");
        retorno.put("counter", counter.incrementAndGet());
        retorno.put("when", LocalDateTime.now());
        retorno.put("elapsedTime", stopWatch.getTotalTimeSeconds());
        return retorno;
	}
    
    @GetMapping("/doerror")
	public String doerror() {
        log.info("## ThrowError");
        if (counter != null) {
            throw new ExceptionDemo("Fake Error");
        }
        return "n/a error";
	}

    @GetMapping("/exit")
	public String exit(@RequestParam(value = "code", defaultValue = "123") Integer code) {
        log.info("## Exiting with code {}", code);
        System.exit(code);
        log.info("n/a exit");
        return "n/a exit";
	}
    
    @GetMapping("/memory")
    public ResponseEntity<String> memory() {
        return new ResponseEntity<>(printMemoryStatus(0), HttpStatus.OK);
    }

    @GetMapping("/info")
    public String hostInfo() {
        return getHostInfo();
    }

    public static String getHostInfo() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String hostname = inetAddress.getHostName();
            String ipAddress = inetAddress.getHostAddress();
            log.info("Hostname {} and Address {}", hostname, ipAddress);
            return "Hostname: " + hostname + " - IP Address: " + ipAddress + " - At: " + LocalTime.now();
        } catch (UnknownHostException e) {
            log.error("Unable to retrieve host information");
            return "Unable to retrieve host information";
        }
    }

    @GetMapping("/oom")
    public void oom() {
        log.info("## Memory");
        List<Object> list = new ArrayList<Object>();
        int cnt = 0;
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("Sleep error", e);
            }
            byte b[] = new byte[10485760]; //10mb
            list.add(b);
            printMemoryStatus(++cnt);
        }
    }

    private String printMemoryStatus(int cnt) {
        int mb = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();
        StringBuilder builder = new StringBuilder();
        builder.append("## ").append(cnt);
        builder.append("\nUsed Memory   : " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " mb");
        builder.append("\nFree Memory   : " + runtime.freeMemory() / mb + " mb");
        builder.append("\nTotal Memory  : " + runtime.totalMemory() / mb + " mb");
        builder.append("\nMax Memory    : " + runtime.maxMemory() / mb + " mb");
        log.info(builder.toString());
        return builder.toString();
    }
}
