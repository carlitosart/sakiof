package com.sakila.sakila.dao;

import com.joutvhu.dynamic.jpa.DynamicQuery;
import com.joutvhu.dynamic.jpa.support.DynamicJpaRepositoryFactoryBean;
import com.sakila.sakila.dto.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Date;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film,Integer> {

    @DynamicQuery(
            value = "select f.* ,(COUNT(f.film_id)-COUNT(r.rental_id)) as count\n" +
                    "from film f \n" +
                    "LEFT JOIN inventory i on f.film_id=i.film_id \n" +
                    "LEFT JOIN store s on s.store_id=i.store_id \n" +
                    "LEFT JOIN address a on a.address_id=s.address_id \n" +
                    "LEFT JOIN city c on c.city_id=a.city_id \n" +
                    "LEFT JOIN rental r on i.inventory_id = r.inventory_id and r.return_date is NULL\n" +
                    "where c.country_id=:countryId \n" +
                    "<#if query?has_content>\n" +
                    "  and MATCH(f.title) AGAINST ( :query IN BOOLEAN MODE)\n" +
                    "</#if>\n"+
                    "<#if actor?has_content>\n" +
                    "  and f.film_id in (select f2.film_id from film f2\n" +
                    "                    INNER JOIN film_actor fa on fa.film_id=f2.film_id\n" +
                    "                    INNER JOIN actor on actor.actor_id=fa.actor_id \n" +
                    "                    WHERE MATCH(actor.first_name,actor.last_name) AGAINST ( :actor IN BOOLEAN MODE))\n" +
                    "</#if>\n" +
                    " GROUP BY f.film_id \n" +
                    "ORDER BY MAX(f.last_update) DESC\n"+
                    "LIMIT :limit offset :page ",
            nativeQuery = true
    )
    List<Film> findFilms(Integer countryId, String query, String actor,Integer page,Integer limit);

    @DynamicQuery(
            value = "select f.*,COUNT(r.rental_id) count2,COUNT(distinct(i.inventory_id))-COUNT(CASE WHEN r.return_date is NULL AND r.rental_id is not NULL THEN 1 END) count\n" +
            "                               from film f \n" +
            "                               LEFT JOIN inventory i on i.film_id=f.film_id \n" +
            "                               LEFT JOIN rental r on i.inventory_id=r.inventory_id " +
                    "<#if date?has_content>\n" +
                    "                      and r.rental_date > :date \n" +
                    "</#if>\n"+
            "                                LEFT JOIN store s on s.store_id=i.store_id \n" +
            "                                LEFT JOIN address a on a.address_id=s.address_id \n" +
            "                                LEFT JOIN city c on c.city_id=a.city_id \n" +
            "                                WHERE c.country_id=:countryId \n" +
            "                                GROUP BY f.film_id \n" +
            "                                HAVING count2!=0 \n" +
            "                                order by count2 DESC \n" +
            "                                LIMIT :limit offset :page",
            nativeQuery = true)
    List<Film> getAllByByDate(Integer countryId,Date date,Integer page,Integer limit);
    @Query(value = "SELECT i.inventory_id \n" +
            "from inventory i \n" +
            "INNER JOIN film f on f.film_id=i.film_id\n" +
            "INNER JOIN store s on s.store_id=i.store_id\n" +
            "INNER JOIN address a on a.address_id=s.address_id\n" +
            "INNER JOIN city c on c.city_id=a.city_id \n" +
            "where i.inventory_id not in (select r.inventory_id from rental r where r.return_date is null)\n" +
            "and f.film_id=:filmId and c.country_id=:countryId LIMIT 1", nativeQuery = true)
    Integer getInventoryId(Integer filmId,Integer countryId);

}
