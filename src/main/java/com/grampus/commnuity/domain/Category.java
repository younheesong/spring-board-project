package com.grampus.commnuity.domain;

public enum Category{
        JAVA, SPRING, JAVASCRIPT, REACT;

        public static Category of(String category) {
                if (category.equalsIgnoreCase("java")) return Category.JAVA;
                else if(category.equalsIgnoreCase("spring")) return Category.SPRING;
                else if(category.equalsIgnoreCase("javascript")) return Category.JAVASCRIPT;
                else if(category.equalsIgnoreCase("react")) return Category.REACT;
                else return null;
        }
}
