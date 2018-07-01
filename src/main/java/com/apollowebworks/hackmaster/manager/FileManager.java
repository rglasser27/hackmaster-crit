package com.apollowebworks.hackmaster.manager;

import com.apollowebworks.hackmaster.model.BodyPart;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.ArrayFieldSetMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
class FileManager {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

	List<BodyPart> readBodyPartFile() {
		DefaultLineMapper<BodyPart> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("id", "lowRoll", "highRoll", "name");
		lineMapper.setLineTokenizer(tokenizer);
		try {
			List<BodyPart> bodyParts = readFile(getBodyPartReader(lineMapper));
			for (int i = 0; i < bodyParts.size(); i ++) {
				bodyParts.get(i).setId(i);
			}
			return bodyParts;
		} catch (Exception e) {
			LOGGER.error("Could not read body part file", e);
			return Collections.emptyList();
		}
	}

	List<String[]> readStringArrayFile(String filename) {
		try {
			return readFile(getStringArrayReader(filename));
		} catch (Exception e) {
			LOGGER.error("Could not read string array file", e);
			return Collections.emptyList();
		}
	}

	private <T> List<T> readFile(FlatFileItemReader<T> itemReader) throws Exception {
		try {
			itemReader.open(new ExecutionContext());
			List<T> response = new ArrayList<>();
			T line;
			do {
				line = itemReader.read();
				if (line != null) {
					response.add(line);
				}
			} while (line != null);
			return response;
		} finally {
			itemReader.close();
		}
	}

	private String getCsvFile(String filename) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		return Objects.requireNonNull(classLoader.getResource("data/" + filename + ".csv")).getFile();
	}

	private FlatFileItemReader<String[]> getStringArrayReader(String shortFileName) {
		DefaultLineMapper<String[]> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
		String filename = getCsvFile(shortFileName);
		FileSystemResource resource = new FileSystemResource(filename);
		FlatFileItemReader<String[]> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(resource);
		itemReader.setLinesToSkip(1);
		lineMapper.setFieldSetMapper(new ArrayFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}

	private FlatFileItemReader<BodyPart> getBodyPartReader(DefaultLineMapper<BodyPart> lineMapper) {
		String filename = getCsvFile("bodyparts");
		FileSystemResource resource = new FileSystemResource(filename);
		FlatFileItemReader<BodyPart> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(resource);
		itemReader.setLinesToSkip(1);
		final BeanWrapperFieldSetMapper<BodyPart> fieldMapper = new BeanWrapperFieldSetMapper<>();
		fieldMapper.setTargetType(BodyPart.class);
		lineMapper.setFieldSetMapper(fieldMapper);
		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}
}
