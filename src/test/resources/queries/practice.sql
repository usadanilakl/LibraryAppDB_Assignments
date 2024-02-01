SELECT B.name,BC.name
FROM books B
         INNER JOIN book_categories BC
                    ON B.book_category_id=BC.id;

-- US01 -1
    select  count(id) from users; -- 4891

    select count(distinct id) from users; -- 4891

-- US01-2
select * from users;

-- US02-1
select count(*) from book_borrow where is_returned=0;

-- US03-1
select name from book_categories;

-- US04-1
select b.name, b.author, b.year, b.isbn, c.name, b.description  from books b join book_categories c on c.id=b.book_category_id
         where b.name = 'Clean Code' and
               author = 'Lionel Messi' and
               isbn = 999239923 and
               year = 2023 and
               c.name = 'Fantasy' and
               b.description = 'Test run for ares AK.';

-- US05-1
select c.name as category, count(*) as popularity
from book_borrow borr
join books b on borr.book_id = b.id
join book_categories c on b.book_category_id = c.id
group by category
order by popularity DESC;

select borrow.id, b.name, c.name from book_borrow borrow
join books b on borrow.book_id = b.id
join book_categories c on b.book_category_id = c.id
where c.name = 'Fantasy';

select  b.name, isbn, year, author,  bc.name from books b join book_categories bc on b.book_category_id = bc.id
where b.name='The Scrum Field Guide DK';

select  b.name, isbn, year, author,  bc.name from books b join book_categories bc on b.book_category_id = bc.id
where b.name='Head First Java DK';