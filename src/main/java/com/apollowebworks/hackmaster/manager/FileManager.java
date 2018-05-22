package com.apollowebworks.hackmaster.manager;

import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.ArrayFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
class FileManager {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

	List<String[]> readFile(String filename) {
		FlatFileItemReader<String[]> itemReader = getReader(filename);
		try {
			itemReader.open(new ExecutionContext());

			boolean done = false;
			List<String[]> response = new ArrayList<>();
			while (!done) {
				try {
					String[] line = itemReader.read();
					if (line == null) {
						done = true;
					} else {
						response.add(line);
					}
				} catch (Exception e) {
					LOGGER.error("Could not read file", e);
				}
			}
			return response;
		} finally {
			itemReader.close();
		}
	}

	private String getFile(String filename) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		return Objects.requireNonNull(classLoader.getResource("data/" + filename + ".csv")).getFile();
	}

	private FlatFileItemReader<String[]> getReader(String shortFileName) {
		String filename = getFile(shortFileName);
		FileSystemResource resource = new FileSystemResource(filename);
		FlatFileItemReader<String[]> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(resource);
		DefaultLineMapper<String[]> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
		lineMapper.setFieldSetMapper(new ArrayFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}
}
