package com.ppc.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestHandler {

    private File testFile;
    private boolean parsed = false;
    private List<Question> questions;

    private void parceQuestion() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(testFile);
        doc.getDocumentElement().normalize();

        NodeList nodesList = doc.getElementsByTagName("questions");
        Question tempQuestion = new Question();
        for(int i = 0; i < nodesList.getLength(); i++) {
            Node currentNode = nodesList.item(i);
            NodeList questionAttributes = currentNode.getChildNodes();
            for(int j = 0; j < questionAttributes.getLength(); j++) {
                Node tmp = questionAttributes.item(j);
                if(tmp.getNodeName() == "variant") {
                    tempQuestion.addVariant(tmp.getNodeValue());
                }
                if(tmp.getNodeName() == "answer") {
                    tempQuestion.setAnswer(tmp.getNodeValue());
                }
                if(tmp.getNodeName() == "question") {
                    tempQuestion.setQuestion(tmp.getNodeValue());
                }
            }
            questions.add(tempQuestion);
        }

    }

    public File getTestFile() {
        return testFile;
    }

    public void setTestFile(final File testFile) {
        this.testFile = testFile;
    }

    public boolean isParsed() {
        return parsed;
    }

    public void setParsed(final boolean parsed) {
        this.parsed = parsed;
    }

    public List<Question> getQuestions() throws ParserConfigurationException, SAXException, IOException {
        if(!parsed) {
            parceQuestion();
        }
        return questions;
    }

    public void setQuestions(final List<Question> questions) {
        this.questions = questions;
    }

}
