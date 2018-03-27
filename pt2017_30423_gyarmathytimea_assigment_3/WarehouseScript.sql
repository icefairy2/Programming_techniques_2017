SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE DATABASE IF NOT EXISTS `warehouse` ;
USE `warehouse` ;

-- -----------------------------------------------------
-- Table `Warehouse`.`Customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `warehouse`.`customer` (
  `id` INT NOT NULL auto_increment,
  `name` VARCHAR(250) NOT NULL,
  `e_mail` VARCHAR(250) NOT NULL,
  `telephone` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Warehouse`.`Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `warehouse`.`product` (
  `id` INT NOT NULL auto_increment,
  `name` VARCHAR(250) NOT NULL,
  `price` FLOAT NOT NULL,
  `weight` FLOAT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Warehouse`.`Order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderdb`;
CREATE TABLE IF NOT EXISTS `warehouse`.`orderdb` (
  `idCustomer` INT NOT NULL,
  `idProduct` INT NOT NULL,
  `quantity` INT NOT NULL,
  CONSTRAINT `product`
    FOREIGN KEY (`idProduct`)
    REFERENCES `warehouse`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `customer`
    FOREIGN KEY (`idCustomer`)
    REFERENCES `warehouse`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
