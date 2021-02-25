package com.woc.common.entity;

import java.util.ArrayList;
import java.util.List;

public class TAlarmExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public TAlarmExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andStudentIdIsNull() {
            addCriterion("student_id is null");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNotNull() {
            addCriterion("student_id is not null");
            return (Criteria) this;
        }

        public Criteria andStudentIdEqualTo(Integer value) {
            addCriterion("student_id =", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotEqualTo(Integer value) {
            addCriterion("student_id <>", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThan(Integer value) {
            addCriterion("student_id >", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("student_id >=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThan(Integer value) {
            addCriterion("student_id <", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThanOrEqualTo(Integer value) {
            addCriterion("student_id <=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIn(List<Integer> values) {
            addCriterion("student_id in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotIn(List<Integer> values) {
            addCriterion("student_id not in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdBetween(Integer value1, Integer value2) {
            addCriterion("student_id between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("student_id not between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmIsNull() {
            addCriterion("course_alarm is null");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmIsNotNull() {
            addCriterion("course_alarm is not null");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmEqualTo(Integer value) {
            addCriterion("course_alarm =", value, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmNotEqualTo(Integer value) {
            addCriterion("course_alarm <>", value, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmGreaterThan(Integer value) {
            addCriterion("course_alarm >", value, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_alarm >=", value, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmLessThan(Integer value) {
            addCriterion("course_alarm <", value, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmLessThanOrEqualTo(Integer value) {
            addCriterion("course_alarm <=", value, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmIn(List<Integer> values) {
            addCriterion("course_alarm in", values, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmNotIn(List<Integer> values) {
            addCriterion("course_alarm not in", values, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmBetween(Integer value1, Integer value2) {
            addCriterion("course_alarm between", value1, value2, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmNotBetween(Integer value1, Integer value2) {
            addCriterion("course_alarm not between", value1, value2, "courseAlarm");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeIsNull() {
            addCriterion("course_alarm_time is null");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeIsNotNull() {
            addCriterion("course_alarm_time is not null");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeEqualTo(String value) {
            addCriterion("course_alarm_time =", value, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeNotEqualTo(String value) {
            addCriterion("course_alarm_time <>", value, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeGreaterThan(String value) {
            addCriterion("course_alarm_time >", value, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeGreaterThanOrEqualTo(String value) {
            addCriterion("course_alarm_time >=", value, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeLessThan(String value) {
            addCriterion("course_alarm_time <", value, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeLessThanOrEqualTo(String value) {
            addCriterion("course_alarm_time <=", value, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeLike(String value) {
            addCriterion("course_alarm_time like", value, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeNotLike(String value) {
            addCriterion("course_alarm_time not like", value, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeIn(List<String> values) {
            addCriterion("course_alarm_time in", values, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeNotIn(List<String> values) {
            addCriterion("course_alarm_time not in", values, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeBetween(String value1, String value2) {
            addCriterion("course_alarm_time between", value1, value2, "courseAlarmTime");
            return (Criteria) this;
        }

        public Criteria andCourseAlarmTimeNotBetween(String value1, String value2) {
            addCriterion("course_alarm_time not between", value1, value2, "courseAlarmTime");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}