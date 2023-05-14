-- auto-generated definition
create table posts
(
    id         uuid not null,
    title      text,
    content    text,
    created_at timestamp
);

alter table posts
    owner to "user";


INSERT INTO public.posts (id, title, content, created_at) VALUES ('1e093b1b-a8c5-4b85-aad6-e96ced38d7e4', 'kotlin', 'kotlin content', '2023-04-13 01:21:51.000000');
INSERT INTO public.posts (id, title, content, created_at) VALUES ('d67fb013-ca50-4de4-b485-417ae396c92a', 'kotlin', 'kotlin coroutines', '2023-04-13 01:22:51.000000');
INSERT INTO public.posts (id, title, content, created_at) VALUES ('d127c3a2-bd03-424e-8e54-3f6c9b90a010', 'kotlin', 'kotlin and vertx', '2023-04-13 01:22:53.000000');
INSERT INTO public.posts (id, title, content, created_at) VALUES ('07c07c47-dde9-4125-9d2b-723de82737d0', 'kotlin', 'vertx', '2023-04-13 01:22:55.000000');
INSERT INTO public.posts (id, title, content, created_at) VALUES ('1458d77e-9296-45a3-a28a-62e3b9050d32', 'kotlin', 'reactive', '2023-04-13 01:24:16.000000');



------------ mongo
{
    "_id" : "1e093b1b-a8c5-4b85-aad6-e96ced38d7e4",
    "title" : "kotlin",
    "content" : "kotlin content",
    "created_at" : "2023-04-13T01:21:51"
}
{
    "_id" : "d67fb013-ca50-4de4-b485-417ae396c92a",
    "title" : "kotlin",
    "content" : "kotlin coroutines",
    "created_at" : "2023-04-13T01:22:51"
}
{
    "_id" : "d127c3a2-bd03-424e-8e54-3f6c9b90a010",
    "title" : "kotlin",
    "content" : "kotlin and vertx",
    "created_at" : "2023-04-13T01:22:53"
}
{
    "_id" : "07c07c47-dde9-4125-9d2b-723de82737d0",
    "title" : "kotlin",
    "content" : "vertx",
    "created_at" : "2023-04-13T01:22:55"
}
{
    "_id" : "1458d77e-9296-45a3-a28a-62e3b9050d32",
    "title" : "kotlin",
    "content" : "reactive",
    "created_at" : "2023-04-13T01:24:16"
}

