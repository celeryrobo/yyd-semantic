package com.yyd.semantic.common.impl;

import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ybnf.compiler.ICompiler;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.impl.MITIECompiler;
import com.yyd.semantic.common.FileUtils;
import com.yyd.semantic.common.SemanticMatching;

@Component("MITIESemanticScene")
public class MITIESemanticScene implements SemanticMatching {
	private static List<ICompiler> compilers = null;

	public MITIESemanticScene(@Value("${mitie.feature.filename}") String featureExtractorFilename) throws Exception {
		if (compilers == null) {
			compilers = new LinkedList<>();
			String filename = FileUtils.getResourcePath() + "semantics/mitie.properties";
			BufferedReader br = FileUtils.fileReader(filename);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] lines = line.split("=", 2);
				if (lines.length == 2) {
					String[] modelNames = lines[1].split(";");
					String categoryFilename = modelNames[0];
					String nerFilename = null;
					if (modelNames.length > 1) {
						nerFilename = modelNames[1];
					}
					compilers.add(new MITIECompiler(categoryFilename, nerFilename, featureExtractorFilename));
				}
			}
			br.close();
		}
	}

	@Override
	public YbnfCompileResult matching(String text) {
		YbnfCompileResult result = null;
		try {
			long startTs = System.currentTimeMillis();
			for (ICompiler compiler : compilers) {
				result = compiler.compile(text);
				if (result != null) {
					break;
				}
			}
			System.out.println("MITIE Semantic Scene Run Time :" + (System.currentTimeMillis() - startTs) + " Service :"
					+ result.getService());
		} catch (Exception e) {
			System.err.println(e);
		}
		return result;
	}

}
