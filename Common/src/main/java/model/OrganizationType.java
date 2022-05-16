package model;

import java.io.Serializable;

/**
 * Перечисление типов Organisation
 *
 * @author Лебедев Вячеслав
 */
public enum OrganizationType implements Serializable {
    COMMERCIAL,
    PUBLIC,
    TRUST,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY
}
