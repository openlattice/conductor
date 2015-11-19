package com.kryptnostic.conductor.orchestra;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.kryptnostic.conductor.v1.NameConstants;
import com.kryptnostic.conductor.v1.objects.ServiceDescriptorSet;
import com.kryptnostic.conductor.v1.processors.MonitoringServiceEntryProcessor;

@Component
public class MonitoringService {
	private final IMap<String, ServiceDescriptorSet> services;
	private final String hazelcastInstanceName;
	@Inject
	public MonitoringService(HazelcastInstance hazelcast) {
		this.services = hazelcast.getMap(NameConstants.CONDUCTOR_MANAGED_SERVICES);
		this.hazelcastInstanceName = hazelcast.getName();
	}

	@Scheduled(fixedRate = 30000)
	public void check() throws IOException {
		services.executeOnEntries(new MonitoringServiceEntryProcessor(hazelcastInstanceName));
	}
}
