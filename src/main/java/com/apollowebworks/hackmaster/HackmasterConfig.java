package com.apollowebworks.hackmaster;

import com.apollowebworks.hackmaster.manager.TableFactory;
import com.apollowebworks.hackmaster.model.AttackType;
import com.apollowebworks.hackmaster.model.BodyPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan
public class HackmasterConfig {
	@Bean
	@Autowired
	public Map<AttackType, List<List<List<String>>>> critTables(TableFactory tableFactory) {
		return tableFactory.createCritTables();
	}

	@Bean
	@Autowired
	public List<BodyPart> bodyParts(TableFactory tableFactory) {
		return tableFactory.createBodyParts();
	}

	@Bean
	@Autowired
	public Map<String, String> effects(TableFactory tableFactory) {
		return tableFactory.createEffectsReference();
	}

}
