-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cookbook
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `recipe_id` int unsigned NOT NULL,
  `name` varchar(80) NOT NULL,
  `quantity` decimal(10,0) DEFAULT NULL,
  `unit` varchar(45) DEFAULT NULL,
  `description` varchar(80) DEFAULT NULL,
  `unit_calories` decimal(8,2) DEFAULT '0.00',
  `unit_protein` decimal(8,2) DEFAULT '0.00',
  `unit_fat` decimal(8,2) DEFAULT '0.00',
  `unit_carbohydrates` decimal(8,2) DEFAULT '0.00',
  PRIMARY KEY (`recipe_id`,`name`),
  KEY `fk_Recipes_idx` (`recipe_id`),
  CONSTRAINT `fk_ingredient_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` (`recipe_id`, `name`, `quantity`, `unit`, `description`, `unit_calories`, `unit_protein`, `unit_fat`, `unit_carbohydrates`) VALUES (100,'Brown sugar',1,'tbsp','broken if large pieces',52.00,0.00,0.00,13.40),(100,'Cooking oil',0,'as needed','for brushing (optional)',884.00,0.00,100.00,0.00),(100,'Ginger',1,'inch','sliced',80.00,1.80,0.80,18.00),(100,'Green onions',1,'pc','cut into long sections',32.00,1.80,0.20,7.30),(100,'Light soy sauce',1,'tbsp','',10.00,1.50,0.00,1.00),(100,'Pork belly',125,'g','cut into 2-inch cubes',518.00,9.34,53.01,0.00),(100,'Water',0,'cup','hot',0.00,0.00,0.00,0.00),(101,'Beef brisket',100,'g','cut into chunks',250.00,26.00,17.00,0.00),(101,'Dark Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(101,'Ginger',5,'g','',80.00,1.80,0.80,18.00),(101,'Light Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(101,'Potato',50,'g','peeled,diced',77.00,2.00,0.10,17.00),(101,'Salt',4,'g','',0.00,0.00,0.00,0.00),(101,'Spring Onion',5,'g','',32.00,1.80,0.20,7.30),(101,'Tomato',50,'g','chopped',18.00,0.90,0.20,3.90),(102,'Dark Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(102,'Light Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(102,'Oil',10,'g','',884.00,0.00,100.00,0.00),(102,'Pepper Powder',3,'g','',251.00,10.39,3.26,63.95),(102,'Pork belly',150,'g','diced,skin removed',518.00,9.34,53.00,0.00),(102,'Refined flour',30,'g','',364.00,10.30,1.00,76.00),(103,'Beef',100,'g','cut into chunks',250.00,26.00,17.00,0.00),(103,'Chinese lettuce',50,'g','wash clean',15.00,1.40,0.20,2.90),(103,'Dark Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(103,'Light Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(103,'Salt',4,'g','',0.00,0.00,0.00,0.00),(103,'Tofu',50,'g','cut into chunks',76.00,8.00,4.80,1.90),(104,'Chicken wings',100,'g','mid-joint,skin on',203.00,30.50,8.10,0.00),(104,'Dark Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(104,'Light Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(104,'Oil',5,'g','',884.00,0.00,100.00,0.00),(104,'Potato',50,'g','peeled,diced',77.00,2.00,0.10,17.00),(104,'Salt',3,'g','',0.00,0.00,0.00,0.00),(105,'Light Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(105,'Oil',5,'g','',884.00,0.00,100.00,0.00),(105,'Pickled mustard green',50,'g','sliced',23.00,2.30,0.40,4.20),(105,'Salt',4,'g','',0.00,0.00,0.00,0.00),(105,'White fish fillet',150,'g','white fish,diced',82.00,18.00,0.70,0.00),(106,'Chicken',150,'g','bone-in,skin-on',239.00,27.30,13.60,0.00),(106,'Chili Pepper',10,'g','',40.00,1.90,0.40,8.80),(106,'Egg',50,'g','',155.00,13.00,11.00,1.10),(106,'Oil',5,'g','',884.00,0.00,100.00,0.00),(106,'Salt',4,'g','',0.00,0.00,0.00,0.00),(106,'Starch',10,'g','',381.00,0.60,0.50,91.30),(107,'Dark Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(107,'Ginger',5,'g','',80.00,1.80,0.80,18.00),(107,'Light Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(107,'Pork spare ribs',150,'g','cut into chunks',278.00,25.70,18.40,0.00),(107,'Salt',4,'g','',0.00,0.00,0.00,0.00),(107,'Sugar',15,'g','',387.00,0.00,0.00,99.80),(108,'Button mushroom',100,'g','stems removed',22.00,3.10,0.30,3.30),(108,'Ground pork',80,'g','Chop up',263.00,18.80,20.80,0.00),(108,'Light Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(108,'Pepper Powder',3,'g','',251.00,10.39,3.26,63.95),(108,'Starch',10,'g','',381.00,0.60,0.50,91.30),(109,'Beef',100,'g','thinly sliced',250.00,26.00,17.00,0.00),(109,'Celery',70,'g','sliced',16.00,0.70,0.20,3.00),(109,'Dark Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(109,'Light Soy Sauce',3,'teaspoon','',10.00,1.50,0.00,1.00),(110,'Egg',70,'g','',155.00,13.00,11.00,1.10),(110,'Oil',5,'g','',884.00,0.00,100.00,0.00),(110,'Salt',4,'g','',0.00,0.00,0.00,0.00),(110,'sugar',5,'g','',387.00,0.00,0.00,99.80),(110,'Tomato',70,'g','cut into chunks',18.00,0.90,0.20,3.90);
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preparationstep`
--

DROP TABLE IF EXISTS `preparationstep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preparationstep` (
  `recipe_id` int unsigned NOT NULL,
  `step` int unsigned NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`recipe_id`,`step`),
  KEY `fk_Recipes_idx` (`recipe_id`),
  CONSTRAINT `fk_preparation_step_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preparationstep`
--

LOCK TABLES `preparationstep` WRITE;
/*!40000 ALTER TABLE `preparationstep` DISABLE KEYS */;
INSERT INTO `preparationstep` (`recipe_id`, `step`, `description`) VALUES (100,1,'Cut pork into 2-inch cubes. Blanch in boiling water with 1/2 slice ginger and 1 green onion for 4 mins. Rinse and drain.'),(100,2,'Heat oil in wok. Sear pork until golden brown. Transfer to clay pot.'),(100,3,'Add soy sauce, sugar, and water to pot. Simmer covered for 50 mins.'),(100,4,'Uncover and reduce sauce until thickened. Rest 5 mins before serving.'),(101,1,'Put ginger and scallions in cold water, blanch the beef and then remove it'),(101,2,'Stir-fry the tomatoes until they release their juice'),(101,3,'Pour the beef into the pot, add light soy sauce, dark soy sauce and salt'),(101,4,'Add water to cover the ingredients and simmer over low heat for 1 hour'),(101,5,'Add the potatoes and cook for another 15 minutes'),(101,6,'Season it according to your own taste'),(102,1,'Marinate the pork with light soy sauce, dark soy sauce and pepper'),(102,2,'Mix the eggs, flour and water to form a batter'),(102,3,'Coat the marinated pork with batter and fry it in hot oil for 3 minutes, then remove it'),(102,4,'Re-fry for 30 seconds to one minute and then remove'),(102,5,'Sprinkle with your favorite seasonings'),(103,1,'Put ginger and scallions in cold water, blanch the beef and then remove it'),(103,2,'Put the beef, tofu and lettuce into the pot and add water'),(103,3,'Add light soy sauce, dark soy sauce and salt to season, and simmer for a while'),(103,4,'Season it according to your own taste'),(104,1,'Put ginger and scallions in cold water, blanch the chicken wings and then remove them'),(104,2,'Pour oil into the pan and fry the chicken wings until both sides are golden brown'),(104,3,'Add enough cold water to cover the chicken wings, then add 2 tablespoons of light soy sauce, 1 tablespoon of dark soy sauce and salt'),(104,4,'Cover the pot and simmer over medium heat for 10 minutes'),(104,5,'Add the potatoes and cook for another 15 to 20 minutes'),(104,6,'After the sauce thickens over high heat, remove from the heat'),(105,1,'Pour oil into the pan and fry the fish until golden brown'),(105,2,'Add the pickled cabbage and stir-fry until fragrant'),(105,3,'Pour in light soy sauce and salt, add water, and bring to a boil over high heat for 5 minutes'),(105,4,'You can add your favorite ingredients according to your own preferences'),(106,1,'Marinate the chicken with salt, pepper, eggs and starch for 20 minutes'),(106,2,'Pour oil into the pan and fry the chicken until it turns golden and crispy'),(106,3,'Add dried chili peppers and continue to stir-fry for about 2 minutes'),(106,4,'Sprinkle with your favorite seasonings'),(107,1,'Put ginger and scallions in cold water, blanch the pork ribs and then remove them'),(107,2,'In a cold pan with oil, melt a handful of sugar until it caramelizes.'),(107,3,'Add the pork ribs and stir-fry until evenly mixed.'),(107,4,'Add 1 tablespoon of light soy sauce, 1 tablespoon of dark soy sauce and salt, pour in hot water to cover the ribs, cover the pot and simmer for 35 minutes.'),(107,5,'After the sauce thickens over high heat, remove from the heat'),(108,1,'Marinate the minced meat with light soy sauce and starch for 10 minutes'),(108,2,'Place the minced meat on the mushrooms'),(108,3,'Pour oil into the pan and fry the beef side down until cooked'),(108,4,'Fry the reverse side for another 3 minutes'),(108,5,'Add light soy sauce, pepper and a little water, and simmer for a while'),(108,6,'Pour in the water thickened with starch to thicken the sauce'),(109,1,'Marinate the minced meat with light soy sauce and dark soy sauce'),(109,2,'Pour oil into the pan and stir-fry the minced meat until cooked'),(109,3,'Season with light soy sauce and salt to your taste'),(109,4,'Add the celery and stir-fry until it is cooked through'),(110,1,'Beat the eggs and pour them into a hot pan with oil.'),(110,2,'Cook until it just solidifies, then take it out.'),(110,3,'In the same pot, add a little more oil, then add diced tomatoes, salt, sugar and a little soy sauce.'),(110,4,'Stir-fry until the tomatoes become soft and juicy, then pour the eggs back into the pan and continue to stir-fry'),(110,5,'You can add a little tomato sauce appropriately to make the flavor more intense');
/*!40000 ALTER TABLE `preparationstep` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `recipe_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `serveamount` int DEFAULT NULL,
  `preparationTime` int unsigned DEFAULT NULL,
  `cookingTime` int unsigned DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `calories` int DEFAULT '0',
  `protein` decimal(8,2) DEFAULT '0.00',
  `carbohydrates` decimal(8,2) DEFAULT '0.00',
  `fat` decimal(8,2) DEFAULT '0.00',
  `fiber` decimal(8,2) DEFAULT '0.00',
  PRIMARY KEY (`recipe_id`),
  UNIQUE KEY `name_UNIQUE` (`name`,`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` (`recipe_id`, `name`, `serveamount`, `preparationTime`, `cookingTime`, `image_url`, `calories`, `protein`, `carbohydrates`, `fat`, `fiber`) VALUES (100,'Hong Shao Rou—Red Braised Pork Belly',1,10,50,'src/images/dishes/hong_shao_rou.png',585,12.00,9.00,55.00,0.00),(101,'Beef Brisket Stew with Tomatoes and Potatoes',1,15,30,'src/images/dishes/beef_brisket_stew_with_tomatoes_and_potatoes.png',450,28.00,35.00,18.00,6.00),(102,'crispy pork',1,15,30,'src/images/dishes/crispy_pork.png',520,25.00,25.00,35.00,2.00),(103,'Beef and Bean Curd Lettuce Casserole',1,15,30,'src/images/dishes/beef_and_bean_curd_lettuce_casserole.png',380,30.00,20.00,22.00,8.00),(104,'Chicken Wing Casserole',1,15,30,'src/images/dishes/chicken_wing_casserole.png',480,32.00,28.00,25.00,4.00),(105,'Pickled Cabbage Fish',1,15,30,'src/images/dishes/pickled_cabbage_fish.png',320,35.00,15.00,12.00,3.00),(106,'Spicy Chicken',1,15,30,'src/images/dishes/spicy_chicken.png',550,40.00,20.00,32.00,2.00),(107,'sweet and sour chops',1,15,30,'src/images/dishes/sweet_and_sour_chops.png',620,35.00,45.00,28.00,3.00),(108,'Stuffed Mushrooms',1,15,30,'src/images/dishes/stuffed_mushrooms.png',280,22.00,15.00,16.00,5.00),(109,'Sauteed Beef with Celery',1,15,30,'src/images/dishes/sauteed_beef_with_celery.png',350,28.00,18.00,20.00,7.00),(110,'scrambled eggs with tomatoes',1,15,30,'src/images/dishes/scrambled_eggs_with_tomatoes.png',220,12.00,15.00,14.00,4.00);
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96653825 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `user_name`, `password`) VALUES (18592666,'qweqwe','qwe'),(23561147,'qwe','qwe'),(35273678,'dddddd','ddd'),(49689913,'lzalzalzalza','password123'),(72386490,'lza123','password123'),(78135363,'lza','password123');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-08 19:43:14
-- Author Ziang Liu
