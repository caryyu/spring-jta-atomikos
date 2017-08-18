package com.github.caryyu.springjtaatomikos.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import java.util.List;

/**
 * <p>ClassName: JPAccessible</p>
 * <p>Description: JPA访问类</p>
 * <p>Author: Cary</p>
 * <p>Date: 2015-9-18</p>
 */
@Configurable
@MappedSuperclass
public class JPAccessible {
    @PersistenceContext
    @Transient
    protected EntityManager entityManager;

    public static EntityManager entityManager(){
        return getEntityManager();
    }

    public static EntityManager getEntityManager() {
        EntityManager em = new JPAccessible().entityManager;
        if (em == null) {
            throw new IllegalStateException("Entity manager has not been injected");
        }
        return em;
    }

    public static Long getCount(Class clazz) {
        return getEntityManager()
                .createQuery("SELECT COUNT(o) FROM " + clazz.getName() + " o ", Long.class)
                .getSingleResult();
    }

    public static <T> List<T> findAll(Class<T> clazz) {
        return getEntityManager()
                .createQuery("SELECT o FROM " + clazz.getName() + " o", clazz)
                .getResultList();
    }

    public static <T> List<T> findAll(Class<T> clazz, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM " + clazz.getName() + " o";
        if (StringUtils.isNotEmpty(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return getEntityManager().createQuery(jpaQuery, clazz).getResultList();
    }

    public static <T> T find(Class<T> clazz, Long id) {
        if (id == null) {
            return null;
        }
        return getEntityManager().find(clazz, id);
    }

    public static <T> List<T> findEntries(Class<T> clazz, int firstResult, int maxResults) {
        return getEntityManager()
                .createQuery("SELECT o FROM " + clazz.getName() + " o", clazz)
                .setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static <T> List<T> findEntries(Class<T> clazz, int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM " + clazz.getName() + " o";
        if (StringUtils.isNotEmpty(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return getEntityManager().createQuery(jpaQuery, clazz)
                .setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Transactional
    public void persist() {
        if(entityManager == null){
            entityManager = getEntityManager();
        }
        entityManager.persist(this);
    }

    @Transactional
    public Object merge() {
        if (entityManager == null) {
            entityManager = getEntityManager();
        }
        Object o = entityManager.merge(this);
        flush();
        return o;
    }

    @Transactional
    public void flush() {
        if(entityManager == null){
            entityManager = getEntityManager();
        }
        entityManager.flush();
    }

    @Transactional
    public void clear() {
        if(entityManager == null){
            entityManager = getEntityManager();
        }
        entityManager.clear();
    }

    @Transactional
    public void remove(){
        if(entityManager == null){
            entityManager = getEntityManager();
        }
        entityManager.remove(this);
    }
}
