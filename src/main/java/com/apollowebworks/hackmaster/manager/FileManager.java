package com.apollowebworks.hackmaster.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.ArrayFieldSetMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
class FileManager {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

	void writeAsJson(String filename, Object object) {
		ObjectMapper mapper = new ObjectMapper();

//		String file2 = getJsonFile(filename);
		Path newFilePath = Paths.get("src/main/resources/"+filename+".json");
		try {
			if (!Files.exists(newFilePath)) {
				Files.createFile(newFilePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		File file = new File(/*"data/" + */filename + ".json");
		try {
			if (Files.exists(newFilePath)) {
				ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
				BufferedWriter bufferedWriter = Files.newBufferedWriter(newFilePath);
				objectWriter.writeValue(bufferedWriter, object);
			}
		} catch (IOException e) {
			LOGGER.error("Can't create file", e);
		}
		file.setWritable(true);
		try {
			mapper.writeValue(file, object);
		} catch (IOException e) {
			LOGGER.error("Can't write to file", e);
		}
	}

	<T> List<T> readFile(String filename, Class<? extends T> type, String... names) {
		FlatFileItemReader<T> itemReader = getReader(filename, type, names);
		try {
			itemReader.setLinesToSkip(1);
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
		} catch (ItemStreamException e) {
			LOGGER.debug("Bad");
		} finally {
			itemReader.close();
		}
		return Collections.emptyList();
	}

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

	private String getCsvFile(String filename) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		return Objects.requireNonNull(classLoader.getResource("data/" + filename + ".csv")).getFile();
	}

	private String getJsonFile(String filename) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		return Objects.requireNonNull(classLoader.getResource("data/" + filename + ".json")).getFile();
	}

	private FlatFileItemReader<String[]> getReader(String shortFileName) {
		String filename = getCsvFile(shortFileName);
		FileSystemResource resource = new FileSystemResource(filename);
		FlatFileItemReader<String[]> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(resource);
		DefaultLineMapper<String[]> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
		lineMapper.setFieldSetMapper(new ArrayFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}

	private <T> FlatFileItemReader<T> getReader(String shortFileName, Class<? extends T> type, String... names) {
		String filename = getCsvFile(shortFileName);
		FileSystemResource resource = new FileSystemResource(filename);
		FlatFileItemReader<T> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(resource);
		final BeanWrapperFieldSetMapper<T> fieldMapper = new BeanWrapperFieldSetMapper<>();
		fieldMapper.setTargetType(type);
		DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(createTokenizer(names));
		lineMapper.setFieldSetMapper(fieldMapper);
		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}

	private <T> FlatFileItemWriter<T> getWriter(String shortFileName, Class<? extends T> type) {
		String filename = getJsonFile(shortFileName);
		FileSystemResource resource = new FileSystemResource(filename);
		FlatFileItemWriter<T> itemWriter = new FlatFileItemWriter<>();
		itemWriter.setResource(resource);
		return itemWriter;
	}

	private LineTokenizer createTokenizer(String[] names) {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		if (names.length > 0) {
			lineTokenizer.setNames(names);
		}
		return lineTokenizer;
	}
}
