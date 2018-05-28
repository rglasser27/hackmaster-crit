package com.apollowebworks.hackmaster.manager;

import com.apollowebworks.hackmaster.model.BodyPart;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.ArrayFieldSetMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
class FileManager {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

	List<BodyPart> readBodyPartFile() {
		FlatFileItemReader<BodyPart> itemReader = getBodyPartReader();
		return readFile(itemReader);
	}

	List<String[]> readStringArrayFile(String filename) {
		FlatFileItemReader<String[]> itemReader = getStringArrayReader(filename);
		return readFile(itemReader);
	}

	private <T> List<T> readFile(FlatFileItemReader<T> itemReader) {
		try {
			itemReader.open(new ExecutionContext());

			boolean done = false;
			List<T> response = new ArrayList<>();
			while (!done) {
				try {
					T line = itemReader.read();
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

	private String getCsvFile(String filename) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		return Objects.requireNonNull(classLoader.getResource("data/" + filename + ".csv")).getFile();
	}


	private FlatFileItemReader<BodyPart> getBodyPartReader() {
		DefaultLineMapper<BodyPart> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(createTokenizer("id", "lowRoll", "highRoll", "name"));
		return getBodyPartReader(lineMapper);
	}

	FlatFileItemReader<String[]> getStringArrayReader(String shortFileName) {
		DefaultLineMapper<String[]> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
		String filename = getCsvFile(shortFileName);
		FileSystemResource resource = new FileSystemResource(filename);
		FlatFileItemReader<String[]> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(resource);
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

	private LineTokenizer createTokenizer(String... names) {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		if (names.length > 0) {
			lineTokenizer.setNames(names);
		}
		return lineTokenizer;
	}
}
